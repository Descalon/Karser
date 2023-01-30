import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

// @KotlinScript annotation marks a script definition class
@KotlinScript(
    // File extension for the script type
    fileExtension = "karser.kts",
    // Compilation configuration for the script type
    compilationConfiguration = KarserScriptConfiguration::class
)
abstract class KarserScript

object KarserScriptConfiguration: ScriptCompilationConfiguration(
    {
        // Implicit imports for all scripts of this type
        defaultImports("models.*", "builders.*")
        jvm {
            // Extract the whole classpath from context classloader and use it as dependencies
            dependenciesFromCurrentContext(wholeClasspath = true)
        }
    }
)
