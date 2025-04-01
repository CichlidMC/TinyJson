package io.github.cichlidmc.tinyjson.test;

import java.io.IOException;
import java.net.URL;

import io.github.cichlidmc.tinyjson.TinyJson;
import io.github.cichlidmc.tinyjson.test.deserialize.Doohickey;
import io.github.cichlidmc.tinyjson.test.framework.TestRunner;
import io.github.cichlidmc.tinyjson.test.tests.ComplexJsonTests;
import io.github.cichlidmc.tinyjson.test.tests.SimpleJsonTests;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;

public class Main {
	public static void main(String[] args) throws IOException {
		TestRunner.of(
				SimpleJsonTests.class,
				ComplexJsonTests.class
		).run();

//		Files.walkFileTree(Paths.get("data"), new SimpleFileVisitor<Path>() {
//			@Override
//			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//				if (file.getFileName().toString().endsWith(".json")) {
//					TinyJson.parseOrThrow(file);
//				}
//				return FileVisitResult.CONTINUE;
//			}
//		});

		parseDoohickey("doohickey.json");
		parseDoohickey("wrong_doohickey.json");
	}

	private static void parseDoohickey(String name) {
		URL url = Main.class.getClassLoader().getResource(name);
		JsonObject parsed = TinyJson.fetchOrThrow(url).asObject();
		Doohickey doohickey = Doohickey.parse(parsed);
		System.out.println(doohickey);
		parsed.forEach((key, value) -> {
			if (!parsed.accessedFields().contains(key)) {
				System.out.println("Unused field: " + key);
			}
		});
	}
}
