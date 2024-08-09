package io.github.cichlidmc.tinyjson.test.framework;

import java.io.InputStream;
import java.lang.reflect.Field;

import io.github.cichlidmc.tinyjson.JsonException;
import io.github.cichlidmc.tinyjson.TinyJson;
import io.github.cichlidmc.tinyjson.value.JsonValue;

public abstract class JsonTest {
	public final String path;

	protected final InputStream stream;
	protected final Field field;

	protected JsonTest(String path, InputStream stream, Field field) {
		this.path = path;
		this.stream = stream;
		this.field = field;
	}

	public void run(TestContext ctx) {
		try {
			this.doRun(ctx);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract void doRun(TestContext ctx) throws ReflectiveOperationException;

	public static class Succeed extends JsonTest {
		protected Succeed(String path, InputStream stream, Field field) {
			super(path, stream, field);
		}

		@Override
		protected void doRun(TestContext ctx) throws ReflectiveOperationException {
			try {
				JsonValue value = TinyJson.parseOrThrow(this.stream);
				JsonValue expected = (JsonValue) this.field.get(null);
				if (!value.equals(expected)) {
					ctx.error("Parse mismatch\nParsed:\n" + value + "\nExpected:\n" + expected);
				}
			} catch (JsonException e) {
				ctx.error("Failed to parse: " + e.getMessage());
			}
		}
	}

	public static class Error extends JsonTest {
		protected Error(String path, InputStream stream, Field field) {
			super(path, stream, field);
		}

		@Override
		protected void doRun(TestContext ctx) throws ReflectiveOperationException {
			try {
				JsonValue value = TinyJson.parseOrThrow(this.stream);
				ctx.error("Parse succeeded when it should've failed\n" + value);
			} catch (JsonException e) {
				String expectedMessage = (String) this.field.get(null);
				if (!e.getMessage().equals(expectedMessage)) {
					ctx.error("Parse failed for the wrong reason\nShould be: " + expectedMessage + "\nGot: " + e.getMessage());
				}
			}
		}
	}
}
