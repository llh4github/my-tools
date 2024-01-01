package io.github.llh4github.mytools.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import io.github.llh4github.mytools.commons.AppErrorEnums
import io.github.llh4github.mytools.commons.AppException
import io.github.llh4github.mytools.convert.dto.ClassInfo
import io.github.llh4github.mytools.convert.dto.FieldInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.jvm.optionals.getOrNull

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

    fun parseJavaClass(typeDeclaration: TypeDeclaration<*>): ClassInfo {
        val fieldNameFromMethod = typeDeclaration.fieldFromGetOrSetMethod()
        val hasLombok = typeDeclaration.hasClassLombokField()
        val fields = typeDeclaration.fields.flatMap { parseFieldInfo(it) }
            .toList()
        return ClassInfo(
            typeDeclaration.nameAsString,
            fields,
            fieldNameFromMethod,
            typeDeclaration.javadocComment.getOrNull()?.asString(),
            hasLombok,
        )
    }

    private fun parseFieldInfo(declaration: FieldDeclaration): List<FieldInfo> {
        val visible = declaration.fieldVisible()
        val doc = declaration.javadocComment.getOrNull()?.asString()
        val jsonAlias = declaration.jsonAlias()
        val apiDoc = declaration.apiDoc()
        return declaration.variables.map {
            val name = it.nameAsString
            var typeName = ""
            var list = emptyList<String>()
            if (it.type.isPrimitiveType) {
                typeName = it.type.asPrimitiveType().asString()
            } else {
                typeName = it.type.asClassOrInterfaceType().nameAsString
                list = it.type.asClassOrInterfaceType().typeArguments.getOrNull()
                    ?.map { t -> t.asString() }
                    ?.toList() ?: emptyList()
            }
            FieldInfo(name, visible, typeName, list, doc, apiDoc, jsonAlias)
        }.toList()
    }


}