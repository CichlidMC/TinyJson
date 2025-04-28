package fish.cichlidmc.tinyjson.value.primitive;

import fish.cichlidmc.tinyjson.value.JsonPrimitive;

public class JsonString extends JsonPrimitive<String> {
	private final String value;

	public JsonString(String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public JsonString copy() {
		return new JsonString(this.value);
	}

	@Override
	public JsonString asString() {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof JsonString && this.value.equals(((JsonString) obj).value);
	}

	@Override
	public String toString() {
		return '"' + this.value + '"';
	}
}
