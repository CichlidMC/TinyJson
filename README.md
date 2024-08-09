# TinyJson
A tiny JSON parser / library.

The TinyJson class provides many methods for parsing JSON into a JsonValue.

The JsonValue API is designed to make deserializing objects with it easy.

All JsonValues track their path for easy identification in errors.

JsonObjects will track which fields are accessed for analysis, like seeing
if you're ignoring any fields by accident.
