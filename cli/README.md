# RoboPOJOGenerator CLI

Generate Java/Kotlin data classes from JSON without IntelliJ.

For calling the generator directly as a library (without CLI/JSON), see [generator/README.md](../generator/README.md).

## Install

```bash
# build and add to PATH
./gradlew :cli:installDist
sudo ln -sf "$PWD/cli/build/install/robopojo/bin/robopojo" /usr/local/bin/robopojo

# from a JSON file (class name inferred from filename)
robopojo -o ./generated response.json

# from stdin
echo '{"id": 1, "name": "Alice"}' | robopojo -n Example -o ./generated
```

## Usage

Run `robopojo --help` for all options. Content is always raw JSON — from a file or stdin.

### Class name inference

When a file argument is provided and `-n` is not, the class name is derived from the filename:

| Filename | Inferred class |
|----------|---------------|
| `response.json` | `Response` |
| `user-profile.json` | `UserProfile` |
| `api_response.json` | `ApiResponse` |

### Frameworks

`none`, `gson`, `jackson`, `moshi`, `logan-square`, `auto-value`, `fast-json`, `jakarta`, `kotlinx`

### Java styles

| Value | Effect |
|-------|--------|
| `CLASS` | Standard Java classes with fields |
| `RECORD` | Java records (immutable, compact syntax) |
| `LOMBOK` | Lombok-annotated classes |

> **Note:** `LOMBOK` style only produces distinct output with `framework: "none"`. With other frameworks, it behaves identically to `CLASS`. The `auto-value` and `kotlinx` frameworks ignore `--style` entirely.

## Examples

```bash
# Jackson annotations
robopojo -o ./gen --framework jackson user.json

# Kotlin data class
robopojo -o ./gen --kotlin user.json

# Java records + Gson + package
robopojo -o ./gen --framework gson --style RECORD -p com.myapp.events event.json

# Pipe from curl
curl -s api.example.com/users/1 | robopojo -n User -o ./gen --framework jackson

# Common Java CLASS flags (--getters/--setters/--to-string are ignored for RECORD style)
robopojo -o ./gen --framework jackson --getters --setters --to-string --primitives --tabs user.json
```
