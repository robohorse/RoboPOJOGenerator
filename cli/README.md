# RoboPOJOGenerator CLI

Generate Java/Kotlin data classes from JSON without IntelliJ.

For calling the generator directly as a library (without CLI/JSON), see [generator/README.md](../generator/README.md).

## Usage

The CLI accepts a single JSON config blob — either as a file path argument or piped to stdin.

### From a file

```bash
./gradlew :cli:run --args='config.json'
```

### From stdin

```bash
echo '{...}' | ./gradlew :cli:run --quiet
```

### Fat JAR

```bash
./gradlew :cli:fatJar
java -jar cli/build/libs/cli-all.jar config.json
```

## Config JSON Schema

```json
{
  "rootClassName": "Account",
  "output": "/tmp/generated",
  "content": {"accountId": "abc", "active": true},
  "package": "com.example.dto",
  "framework": "jackson",
  "javaStyle": "CLASS",
  "useKotlin": false,
  "useGetters": false,
  "useSetters": false,
  "useStrings": false,
  "javaPrimitives": false,
  "useTabsIndentation": false,
  "useLombokValue": false,
  "kotlinNullableFields": true,
  "useKotlinDataClass": true,
  "useKotlinSingleDataClass": false,
  "useKotlinParcelable": false,
  "useMoshiAdapter": false
}
```

### Required fields

| Field | Description |
|-------|-------------|
| `rootClassName` | Root class name |
| `output` | Output directory (created if missing) |
| `content` | The JSON payload to generate classes from |

### Optional fields

| Field | Default | Description |
|-------|---------|-------------|
| `package` | _(none)_ | Package declaration |
| `framework` | `"none"` | Serialization framework (see below) |
| `javaStyle` | `"CLASS"` | Java output style: `CLASS`, `RECORD`, or `LOMBOK` |
| `useKotlin` | `false` | Generate Kotlin instead of Java |
| `useGetters` | `false` | Generate getter methods |
| `useSetters` | `false` | Generate setter methods |
| `useStrings` | `false` | Generate `toString()` |
| `javaPrimitives` | `false` | Use Java primitives (`int`, `boolean`) |
| `useTabsIndentation` | `false` | Tab indentation (default: 4 spaces) |
| `useLombokValue` | `false` | Lombok `@Value` annotation (when `javaStyle` is `LOMBOK`) |
| `kotlinNullableFields` | `true` | Nullable fields in Kotlin |
| `useKotlinDataClass` | `true` | Kotlin data classes |
| `useKotlinSingleDataClass` | `false` | All classes in one `.kt` file |
| `useKotlinParcelable` | `false` | Kotlin Parcelable |
| `useMoshiAdapter` | `false` | Moshi adapter generation |

### Frameworks

`none`, `gson`, `jackson`, `moshi`, `logan-square`, `auto-value`, `fast-json`, `jakarta`, `kotlinx`

### Java styles

| Value | Effect |
|-------|--------|
| `CLASS` | Standard Java classes with fields, getters/setters |
| `RECORD` | Java records (immutable, compact syntax) |
| `LOMBOK` | Lombok-annotated classes |

The `javaStyle` field combines with `framework` to select the output format. For example, `"framework": "jackson"` + `"javaStyle": "RECORD"` produces Jackson-annotated Java records.

## Examples

### Java + Jackson

```bash
echo '{
  "rootClassName": "UserResponse",
  "output": "./generated",
  "package": "com.myapp.api",
  "framework": "jackson",
  "content": {
    "userId": 123,
    "name": "Alice",
    "address": {"street": "123 Main", "city": "NYC"}
  }
}' | java -jar cli/build/libs/cli-all.jar
```

Produces `UserResponse.java` and `Address.java` in `./generated/`.

### Kotlin data class

```bash
echo '{
  "rootClassName": "Config",
  "output": "./generated",
  "useKotlin": true,
  "content": {"host": "localhost", "port": 8080, "debug": true}
}' | java -jar cli/build/libs/cli-all.jar
```

### Java records + Gson

```bash
echo '{
  "rootClassName": "Event",
  "output": "./generated",
  "package": "com.myapp.events",
  "framework": "gson",
  "javaStyle": "RECORD",
  "content": {"eventType": "click", "timestamp": 1700000000}
}' | java -jar cli/build/libs/cli-all.jar
```

### Lombok @Value

```bash
echo '{
  "rootClassName": "Settings",
  "output": "./generated",
  "package": "com.myapp",
  "javaStyle": "LOMBOK",
  "useLombokValue": true,
  "content": {"theme": "dark", "fontSize": 14}
}' | java -jar cli/build/libs/cli-all.jar
```
