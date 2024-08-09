package io.github.cichlidmc.tinyjson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.cichlidmc.tinyjson.parser.ValueParser;
import io.github.cichlidmc.tinyjson.parser.util.ParseInput;
import io.github.cichlidmc.tinyjson.value.JsonValue;

/**
 * Primary interface for TinyJson. Provides several methods for parsing JSON from various sources:
 * <ul>
 *     <li>Strings</li>
 *     <li>Files</li>
 *     <li>Paths</li>
 *     <li>URIs</li>
 *     <li>URLs</li>
 *     <li>InputStreams</li>
 *     <li>Readers</li>
 * </ul>
 */
public interface TinyJson {
	// String input

	static JsonValue parse(String string) throws IOException {
		return parse(new StringReader(string));
	}

	static JsonValue parseOrThrow(String string) {
		return parseOrThrow(new StringReader(string));
	}

	// File input

	static JsonValue parse(File file) throws IOException {
		return parse(file.toPath());
	}

	static JsonValue parseOrThrow(File file) {
		return parseOrThrow(file.toPath());
	}

	// Path input

	static JsonValue parse(Path file) throws IOException {
		return parse(Files.newBufferedReader(file));
	}

	static JsonValue parseOrThrow(Path file) {
		try {
			return parse(Files.newBufferedReader(file));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	// URI input

	static JsonValue fetch(URI uri) throws IOException {
		return fetch(uri.toURL());
	}

	static JsonValue fetchOrThrow(URI uri) {
		try {
			return fetchOrThrow(uri.toURL());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	// URL input

	static JsonValue fetch(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		connection.connect();
		return parse(connection.getInputStream());
	}

	static JsonValue fetchOrThrow(URL url) {
		try {
			return fetch(url);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	// InputStream input

	static JsonValue parse(InputStream stream) throws IOException {
		return parse(new InputStreamReader(stream));
	}

	static JsonValue parseOrThrow(InputStream stream) {
		return parseOrThrow(new InputStreamReader(stream));
	}

	// Reader input

	static JsonValue parse(Reader reader) throws IOException {
		return ValueParser.parse(new ParseInput(reader));
	}

	static JsonValue parseOrThrow(Reader reader) {
		try {
			return parse(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
