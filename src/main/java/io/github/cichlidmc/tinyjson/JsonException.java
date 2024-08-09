package io.github.cichlidmc.tinyjson;

import io.github.cichlidmc.tinyjson.value.JsonValue;

/**
 * Exception that may be thrown when managing JSON. This may be thrown for a parsing error or
 * for a deserialization error (such as calling {@link JsonValue#asObject()} on a non-object)
 */
public class JsonException extends RuntimeException {
	public JsonException(String message) {
		super(message);
	}
}