package fish.cichlidmc.tinyjson.test.framework;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TestRunner {
	private final List<JsonTest> tests;
	private int fails;

	public TestRunner(List<JsonTest> tests) {
		this.tests = tests;
	}

	public void run() {
		for (JsonTest test : this.tests) {
			test.run(error -> {
				this.fails++;
				System.out.println("Test failed: " + test.path);
				System.out.println(error);
				System.out.println();
			});
		}
		System.out.printf("%d tests failed (out of %d)\n", this.fails, this.tests.size());
	}

	public TestRunner filter(String path) {
		this.tests.removeIf(test -> !test.path.equals(path));
		return this;
	}

	public static TestRunner of(Class<?>... classes) {
		List<JsonTest> tests = new ArrayList<>();

		for (Class<?> clazz : classes) {
			String group = clazz.getAnnotation(Group.class).value();
			for (Field field : clazz.getDeclaredFields()) {
				Test test = field.getAnnotation(Test.class);
				if (test == null)
					continue;

				if (!Modifier.isPublic(field.getModifiers())) {
					throw new RuntimeException("Field should be public: " + field);
				} else if (!Modifier.isStatic(field.getModifiers())) {
					throw new RuntimeException("Field should be static: " + field);
				} else if (!Modifier.isFinal(field.getModifiers())) {
					throw new RuntimeException("Field should be final: " + field);
				}

				String path = "tests/" + group + '/' + getFileName(field, test) + ".json";
				InputStream stream = getResource(path);

				if (test.fails()) {
					tests.add(new JsonTest.Error(path, stream, field));
				} else {
					tests.add(new JsonTest.Succeed(path, stream, field));
				}
			}
		}

		return new TestRunner(tests);
	}

	private static String getFileName(Field field, Test test) {
		if (!test.file().isEmpty())
			return test.file();

		return field.getName().toLowerCase(Locale.ROOT);
	}

	private static InputStream getResource(String name) {
		return Objects.requireNonNull(TestRunner.class.getClassLoader().getResourceAsStream(name), name);
	}
}
