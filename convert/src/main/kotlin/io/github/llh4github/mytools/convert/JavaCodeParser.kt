package io.github.llh4github.mytools.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import io.github.llh4github.mytools.commons.AppErrorEnums
import io.github.llh4github.mytools.commons.AppException
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 *
 * Created At 2023/12/30 16:44
 * @author llh
 */
class JavaCodeParser {
    private val logger = KotlinLogging.logger {}

    private fun parseJavaGrammar(code: String): CompilationUnit {
        try {
            return StaticJavaParser.parse(code)
        } catch (e: Exception) {
            logger.error { "Java代码解析出错: ${e.message}" }
            throw AppException(AppErrorEnums.LIB_ERROR, "Java代码解析出错", e)
        }
    }
}