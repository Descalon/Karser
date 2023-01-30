package utils

import KarserScript
import script.models.Language
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.StringScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

fun evalFile(input: String): ResultWithDiagnostics<EvaluationResult> {
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<KarserScript>()
    return BasicJvmScriptingHost().eval(StringScriptSource(input), compilationConfiguration, null)
}

fun evalLanguage(input: String): Language {
    val result = evalFile(input)
    return (result.valueOrThrow().returnValue as ResultValue.Value).value as Language
}