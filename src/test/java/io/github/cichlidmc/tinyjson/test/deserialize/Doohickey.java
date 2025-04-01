package io.github.cichlidmc.tinyjson.test.deserialize;

import java.util.List;
import java.util.stream.Collectors;

import io.github.cichlidmc.tinyjson.value.primitive.JsonNumber;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;
import io.github.cichlidmc.tinyjson.value.JsonValue;

public class Doohickey {
	private final String name;
	private final int i;
	private final double d;
	private final Thingy thingy;

	public Doohickey(String name, int i, double d, Thingy thingy) {
		this.name = name;
		this.i = i;
		this.d = d;
		this.thingy = thingy;
	}

	public static Doohickey parse(JsonObject json) {
		String name = json.get("name").asString().value();
		int i = json.get("i").asNumber().strictValue().intValue();
		double d = json.get("d").asNumber().value();
		Thingy thingy = Thingy.parse(json.get("thingy").asObject());
		return new Doohickey(name, i, d, thingy);
	}

	public static class Thingy {
		private final String name;
		private final List<Double> numbers;

		public Thingy(String name, List<Double> numbers) {
			this.name = name;
			this.numbers = numbers;
		}

		public static Thingy parse(JsonObject json) {
			String name = json.get("name").asString().value();
			List<Double> numbers = json.get("numbers").asArray().stream()
					.map(JsonValue::asNumber)
					.map(JsonNumber::value)
					.collect(Collectors.toList());
			return new Thingy(name, numbers);
		}
	}
}
