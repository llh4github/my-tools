package io.github.llh4github.mytools.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.TypeDeclaration
import io.github.llh4github.mytools.commons.AppErrorEnums
import io.github.llh4github.mytools.commons.AppException
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 *
 * Created At 2023/12/30 23:35
 * @author llh
 */
internal object JavaCodeParserInner {

    private val logger = KotlinLogging.logger {}
    fun parseJavaGrammar(code: String): CompilationUnit {
        try {
            return StaticJavaParser.parse(code)
        } catch (e: Exception) {
            logger.error { "Java代码解析出错: ${e.message}" }
            throw AppException(AppErrorEnums.LIB_ERROR, "Java代码解析出错", e)
        }
    }

    fun parseJavaClass(typeDeclaration: TypeDeclaration<*>) {

    }

    /**
     * getter,setter方法转换为属性名
     *
     * 简单处理，不一定准确
     */
    fun methodToFieldName(method: String): String {
        if (method.startsWith("get")) {
            return method.removePrefix("get").replaceFirstChar { it.lowercaseChar() }
        }
        if (method.startsWith("set")) {
            return method.removePrefix("set").replaceFirstChar { it.lowercaseChar() }
        }
        return method
    }
}