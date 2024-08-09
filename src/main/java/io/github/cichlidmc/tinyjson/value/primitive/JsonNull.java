package io.github.cichlidmc.tinyjson.value.primitive;

import io.github.cichlidmc.tinyjson.value.JsonPrimitive;

public class JsonNull extends JsonPrimitive<Object> {
	@Override
	public Object value() {
		return null;
	}

	@Override
	public JsonNull copy() {
		return new JsonNull();
	}

	@Override
	public JsonNull asNull() {
		return this;
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof JsonNull;
	}

	public static JsonNull create() {
		return new JsonNull();
	}
}
