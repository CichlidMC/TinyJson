package io.github.cichlidmc.tinyjson.value;

/**
 * A JsonPrimitive is a JsonValue that holds some direct value and not other JsonValues.
 */
public abstract class JsonPrimitive<T> extends JsonValue {
	/**
	 * Value held by this JSON value. Always non-null UNLESS this is a JsonNull.
	 */
	public abstract T value();
}
