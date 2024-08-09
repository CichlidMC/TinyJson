package io.github.cichlidmc.tinyjson.value.primitive;

import io.github.cichlidmc.tinyjson.JsonException;
import io.github.cichlidmc.tinyjson.value.JsonPrimitive;

public class JsonNumber extends JsonPrimitive<Double> {
	protected final Double value;

	public JsonNumber(Double value) {
		this.value = value;
	}

	public JsonNumber(int i) {
		this((double) i);
	}

	public JsonNumber(long l) {
		this((double) l);
	}

	public JsonNumber(float f) {
		this((double) f);
	}

	@Override
	public Double value() {
		return this.value;
	}

	/**
	 * Get a wrapper for the value of this JsonNumber that validates that the value is valid for the desired type.
	 * For example, for a value of 1.1, calling intValue will throw.
	 */
	public Number strictValue() {
		return new StrictAccess();
	}

	@Override
	public JsonNumber copy() {
		return new JsonNumber(this.value);
	}

	@Override
	public JsonNumber asNumber() {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof JsonNumber && this.value.equals(((JsonNumber) obj).value);
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	public class StrictAccess extends Number {
		@Override
		public byte byteValue() {
			if (value.byteValue() == value) {
				return value.byteValue();
			}
			throw this.typeError("a byte");
		}

		@Override
		public short shortValue() {
			if (value.shortValue() == value) {
				return value.shortValue();
			}
			throw this.typeError("a short");
		}

		@Override
		public int intValue() {
			if (value.intValue() == value) {
				return value.intValue();
			}
			throw this.typeError("an int");
		}

		@Override
		public long longValue() {
			if (value.longValue() == value) {
				return value.longValue();
			}
			throw this.typeError("a long");
		}

		@Override
		public float floatValue() {
			if (value.floatValue() == value) {
				return value.floatValue();
			}
			throw this.typeError("a float");
		}

		@Override
		public double doubleValue() {
			return value;
		}

		private JsonException typeError(String type) {
			if (hasPath()) {
				return new JsonException(getPath() + " is not " + type);
			} else {
				return new JsonException("Not " + type);
			}
		}
	}
}
