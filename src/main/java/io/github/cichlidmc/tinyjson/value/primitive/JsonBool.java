package io.github.cichlidmc.tinyjson.value.primitive;

import io.github.cichlidmc.tinyjson.value.JsonPrimitive;

public class JsonBool extends JsonPrimitive<Boolean> {
	private final Boolean value;

	public JsonBool(Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean value() {
		return this.value;
	}

	@Override
	public JsonBool copy() {
		return new JsonBool(this.value);
	}

	@Override
	public JsonBool asBoolean() {
		return this;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof JsonBool && ((JsonBool) obj).value.equals(this.value);
	}

	public static JsonBool createTrue() {
		return new JsonBool(true);
	}

	public static JsonBool createFalse() {
		return new JsonBool(false);
	}
}
