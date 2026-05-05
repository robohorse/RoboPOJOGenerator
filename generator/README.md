# RoboPOJOGenerator Library

Use the generator directly as a Java/Kotlin library dependency without any CLI or JSON parsing.

For the JSON-config CLI wrapper, see [cli/README.md](../cli/README.md).

## Dependencies

Add the `:generator` and `:core` modules to your project. The generator has no IntelliJ dependencies.

## Usage

```kotlin
import com.robohorse.robopojogenerator.GeneratorFacade
import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.JavaStyle
import java.io.File

val model = GenerationModel(
    rootClassName = "Account",
    content = """{"accountId": "abc", "active": true}""",
    annotationEnum = FrameworkVW.fromString("jackson", JavaStyle.CLASS)
)

val outputDir = File("/tmp/generated")
GeneratorFacade().generate(model, outputDir, packageName = "com.example.dto")
```

Only `rootClassName` and `content` are required. All other fields have sensible defaults.

Generated files are written directly to `outputDir`.

## API

### `GeneratorFacade.generate(model, outputDir, packageName?)`

| Parameter | Type | Description |
|-----------|------|-------------|
| `model` | `GenerationModel` | All generation settings |
| `outputDir` | `File` | Directory to write generated files (created if it does not exist) |
| `packageName` | `String?` | Package declaration (optional) |

### `FrameworkVW.fromString(name, style)`

Factory for selecting a serialization framework and Java style.

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `name` | `String` | — | Framework: `none`, `gson`, `jackson`, `moshi`, `logan-square`, `auto-value`, `fast-json`, `jakarta`, `kotlinx` |
| `style` | `JavaStyle` | `CLASS` | `CLASS`, `RECORD`, or `LOMBOK` |

### `GenerationModel` fields

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `rewriteClasses` | `Boolean` | — | Always `true` for library usage |
| `useKotlin` | `Boolean` | — | Generate Kotlin instead of Java |
| `annotationEnum` | `FrameworkVW` | — | Framework + style (use `FrameworkVW.fromString`) |
| `rootClassName` | `String` | — | Name of the root generated class |
| `content` | `String` | — | JSON string to generate classes from |
| `useSetters` | `Boolean` | — | Generate setter methods |
| `useGetters` | `Boolean` | — | Generate getter methods |
| `useStrings` | `Boolean` | — | Generate `toString()` |
| `useKotlinSingleDataClass` | `Boolean` | — | All Kotlin classes in one file |
| `useKotlinParcelable` | `Boolean` | — | Kotlin Parcelable |
| `kotlinNullableFields` | `Boolean` | — | Nullable fields in Kotlin |
| `javaPrimitives` | `Boolean` | — | Use Java primitives (`int`, `boolean`) |
| `useTabsIndentation` | `Boolean` | — | Tab indentation (default: 4 spaces) |
| `useLombokValue` | `Boolean` | — | Lombok `@Value` annotation |
| `useMoshiAdapter` | `Boolean` | — | Moshi adapter generation |
| `useKotlinDataClass` | `Boolean` | — | Kotlin data classes |

## Examples

### Java records + Jackson

```kotlin
val model = GenerationModel(
    rootClassName = "UserResponse",
    content = """{"userId": 123, "name": "Alice"}""",
    annotationEnum = FrameworkVW.fromString("jackson", JavaStyle.RECORD)
)

GeneratorFacade().generate(model, File("./out"), "com.example.api")
```

### Kotlin data class

```kotlin
val model = GenerationModel(
    rootClassName = "Config",
    content = """{"host": "localhost", "port": 8080}""",
    useKotlin = true,
    annotationEnum = FrameworkVW.fromString("kotlinx")
)

GeneratorFacade().generate(model, File("./out"))
```

### Lombok

```kotlin
val model = GenerationModel(
    rootClassName = "Settings",
    content = """{"theme": "dark", "fontSize": 14}""",
    annotationEnum = FrameworkVW.fromString("none", JavaStyle.LOMBOK),
    useLombokValue = true
)

GeneratorFacade().generate(model, File("./out"), "com.myapp")
```
