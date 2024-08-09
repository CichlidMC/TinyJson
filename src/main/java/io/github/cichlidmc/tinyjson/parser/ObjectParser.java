package io.github.cichlidmc.tinyjson.parser;

import static io.github.cichlidmc.tinyjson.parser.util.ParserUtils.OBJ_END;

import java.io.IOException;

import io.github.cichlidmc.tinyjson.parser.util.ParseInput;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;
import io.github.cichlidmc.tinyjson.value.primitive.JsonString;
import io.github.cichlidmc.tinyjson.value.JsonValue;

public class ObjectParser {
	public static final char COLON = ':';
	public static final char COMMA = ',';

	static JsonObject parse(ParseInput input) throws IOException {
		input.next(); // discard opening {
		input.skipWhitespace();

		JsonObject object = new JsonObject();
		boolean first = true;
		while (input.peekNonWhitespace() != OBJ_END) {
			if (!first) {
				// for all entries except the first one, check for a preceding comma.
				if (input.next() != COMMA) {
					throw input.errorAt("Expected comma before entry");
				}
				// check for OBJ_END again in case of trailing comma
				if (input.peekNonWhitespace() == OBJ_END) {
					throw input.errorAt("Expected entry after comma");
				}
			}

			first = false;

			JsonValue key = ValueParser.parse(input);
			if (!(key instanceof JsonString)) {
				throw input.errorAt("Expected string key in object");
			}

			if (input.peekNonWhitespace() != COLON) {
				throw input.errorAt("Expected colon before value");
			}
			input.next(); // consume colon

			JsonValue value = ValueParser.parse(input);
			object.put(((JsonString) key).value(), value);
		}

		// consume closing }
		input.next();

		return object;
	}
}
