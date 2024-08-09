package io.github.cichlidmc.tinyjson.parser;

import static io.github.cichlidmc.tinyjson.parser.util.ParserUtils.*;

import java.io.IOException;

import io.github.cichlidmc.tinyjson.parser.util.ParseInput;
import io.github.cichlidmc.tinyjson.value.primitive.JsonBool;
import io.github.cichlidmc.tinyjson.value.primitive.JsonNull;
import io.github.cichlidmc.tinyjson.value.JsonValue;

public class ValueParser {
	public static JsonValue parse(ParseInput input) throws IOException {
		input.skipWhitespace();
		char next = input.peekOrThrow();

		if (next == OBJ_START) {
			return ObjectParser.parse(input);
		} else if (next == ARRAY_START) {
			return ArrayParser.parse(input);
		} else if (next == QUOTE) {
			return StringParser.parse(input);
		} else if (next == NEGATIVE_SIGN || isNumber(next)) {
			return NumberParser.parse(input);
		}

		// can't tell by prefix, check for whole strings
		char[] next4 = input.peek(4);

		if (charsEqual(next4, TRUE)) {
			input.next(4); // consume peeked chars
			return JsonBool.createTrue();
		} else if (charsEqual(next4, NULL)) {
			input.next(4);
			return JsonNull.create();
		}

		// of course false has to be longer
		if (charsEqual(input.peek(5), FALSE)) {
			input.next(5);
			return JsonBool.createFalse();
		}

		// none of the above
		throw input.errorAt("Could not parse value");
	}
}
