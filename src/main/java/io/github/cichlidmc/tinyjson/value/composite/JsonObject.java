package io.github.cichlidmc.tinyjson.value.composite;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import io.github.cichlidmc.tinyjson.JsonException;
import io.github.cichlidmc.tinyjson.value.JsonValue;
import io.github.cichlidmc.tinyjson.value.primitive.JsonBool;
import io.github.cichlidmc.tinyjson.value.primitive.JsonNull;
import io.github.cichlidmc.tinyjson.value.primitive.JsonNumber;
import io.github.cichlidmc.tinyjson.value.primitive.JsonString;

/**
 * Class for objects represented in JSON. Stores a map of keys to other JsonValues.
 * Also tracks which fields are directly accessed for verification purposes.
 */
public class JsonObject extends JsonValue {
	private final LinkedHashMap<String, JsonValue> entries = new LinkedHashMap<>();
	private final Set<String> accessed = new HashSet<>();

	public JsonObject put(String key, JsonValue value) {
		this.setChildPath(key, value);
		JsonValue removed = this.entries.put(key, value);
		if (removed != null) {
			removed.setPath(null);
		}
		return this;
	}

	// utilities for adding types directly
	public JsonObject put(String key, boolean value)	{ return this.put(key, new JsonBool(value));	}
	public JsonObject putNull(String key)				{ return this.put(key, new JsonNull());			}
	public JsonObject put(String key, double value)		{ return this.put(key, new JsonNumber(value));	}
	public JsonObject put(String key, String value)		{ return this.put(key, new JsonString(value));	}

	public JsonValue getNullable(String key) {
		this.accessed.add(key);
		return this.entries.get(key);
	}

	public Optional<JsonValue> getOptional(String key) {
		return Optional.ofNullable(this.getNullable(key));
	}

	/**
	 * Get the value at the given key, throwing if it's missing.
	 */
	public JsonValue get(String key) {
		JsonValue value = this.getNullable(key);
		if (value == null) {
			throw new JsonException(this.makePath(key) + " does not exist");
		}
		return value;
	}

	public boolean contains(String key) {
		return this.entries.containsKey(key);
	}

	public int size() {
		return this.entries.size();
	}

	public void forEach(BiConsumer<String, JsonValue> consumer) {
		this.entries.forEach(consumer);
	}

	/**
	 * @return the set of fields that have been accessed directly via {@link #getNullable(String)}.
	 */
	public Set<String> accessedFields() {
		return this.accessed;
	}

	/**
	 * @return a read-only set of entries in this object.
	 */
	public Set<Map.Entry<String, JsonValue>> entrySet() {
		return Collections.unmodifiableSet(this.entries.entrySet());
	}

	@Override
	public JsonObject copy() {
		JsonObject copy = new JsonObject();
		this.forEach((k, v) -> copy.put(k, v.copy()));
		return copy;
	}

	@Override
	public JsonObject asObject() {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof JsonObject && ((JsonObject) obj).entries.equals(this.entries);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		int i = 0;
		for (Map.Entry<String, JsonValue> entry : this.entrySet()) {
			// increase indent of all lines
			String string = entry.getValue().toString().replace("\n", "\n\t");
			builder.append("\n\t").append('"').append(entry.getKey()).append("\": ").append(string);
			if (i != this.entries.size() - 1) {
				builder.append(',');
			}
			i++;
		}
		return builder.append("\n}").toString();
	}

	@Override
	public void setPath(String path) {
		super.setPath(path);
		this.forEach((key, value) -> {
			// reset path first
			value.setPath(null);
			this.setChildPath(key, value);
		});
	}

	private void setChildPath(String key, JsonValue value) {
		value.setPath(this.makePath(key));
	}

	private String makePath(String key) {
		return this.getPath() + '.' + key;
	}
}
