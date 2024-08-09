package io.github.cichlidmc.tinyjson.test.tests;

import io.github.cichlidmc.tinyjson.test.framework.Group;
import io.github.cichlidmc.tinyjson.test.framework.Test;
import io.github.cichlidmc.tinyjson.value.composite.JsonArray;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;
import io.github.cichlidmc.tinyjson.value.JsonValue;

@Group("complex")
public class ComplexJsonTests {
	@Test
	public static final JsonValue NUMBERS = new JsonArray()
			.add(1)
			.add(2)
			.add(0)
			.add(0)
			.add(0.1)
			.add(0.00001)
			.add(0)
			.add(0)
			.add(1.5e9)
			.add(10_000e-3)
			.add(2.5e-1);

	@Test
	public static final JsonValue EVERYTHING = new JsonObject()
			.put("test", true)
			.putNull("test2")
			.put("test3: false", "true")
			.put("list", new JsonArray()
					.add(new JsonArray()
							.add("list2")
							.add(new JsonArray()
									.add("list3")
							)
							.add(new JsonObject()
									.put("veryNested", new JsonObject()
											.put("false", true)
									)
							)
					)
					.add(1e-20)
			);
}
