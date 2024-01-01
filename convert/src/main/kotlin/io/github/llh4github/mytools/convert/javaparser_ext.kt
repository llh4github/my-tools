package io.github.llh4github.mytools.convert

import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr
import io.github.llh4github.mytools.convert.constant.FieldVisible
import io.github.llh4github.mytools.convert.constant.LombokAnnoName

/**
 * 字段可见性
 */
internal fun FieldDeclaration.fieldVisible(): FieldVisible {
    if (this.isPublic) {
        return FieldVisible.PUBLIC
    }
    if (this.markNoAccess()) {
        return FieldVisible.MARK_NO_ACCESS
    }
    return FieldVisible.PRIVATE
}

internal fun FieldDeclaration.apiDoc(): String? {
    val anno = this.annotations.firstOrNull { it.nameAsString == "ApiModelProperty" }
    if (anno == null) return null
    return when (anno) {
        is SingleMemberAnnotationExpr -> {
            return anno.memberValue.asStringLiteralExpr().asString()
        }

        is NormalAnnotationExpr -> {
            anno.pairs.firstOrNull { ele -> ele.name.asString() == "value" }
                ?.value?.asStringLiteralExpr()?.asString()
        }

        else -> null
    }
}

internal fun FieldDeclaration.jsonAlias(): List<String> {
    val list = mutableListOf<String>()
    this.annotations
        .filter { it.nameAsString == "JsonAlias" }
        .forEach {
            if (it is SingleMemberAnnotationExpr) {
                (it.memberValue.asArrayInitializerExpr()).values.forEach { ele ->
                    val alias = ele.asStringLiteralExpr().asString()
                    list.add(alias)
                }
            }
            if (it is NormalAnnotationExpr) {
                it.pairs.filter { ele -> ele.nameAsString == "value" }
                    .forEach { ele ->
                        ele.value.asArrayInitializerExpr()
                            .values.forEach { alias ->
                                list.add(alias.asStringLiteralExpr().asString())
                            }
                    }
            }
        }
    return list
}

/**
 * 是否被lombok注解标记为不可访问
 */
internal fun FieldDeclaration.markNoAccess(): Boolean {
    return this.annotations
        .filter { it.nameAsString == "Getter" || it.nameAsString == "Setter" }
        .map {
            when (it) {
                is SingleMemberAnnotationExpr -> {
                    LombokAnnoName.privateAccessLevel.contains(it.memberValue.asFieldAccessExpr().nameAsString)
                }

                is NormalAnnotationExpr -> {
                    it.pairs.filter { ele -> ele.nameAsString == "value" }
                        .map { ele -> ele.value.asFieldAccessExpr().nameAsString }
                        .any { ele -> LombokAnnoName.privateAccessLevel.contains(ele) }
                }

                else -> {
                    false
                }
            }
        }.any { it }
}

/**
 * 成员字段名。非静态字段。
 */
internal fun TypeDeclaration<*>.memberFieldNames(): List<String> {
    return this.fields.filter { !it.isStatic }
        .flatMap { it.variables }
        .map { it.nameAsString }
        .filter { it.isNotEmpty() }
        .toList()
}

/**
 * 类上是有否有@Data,@Setter,@Getter之中的注解
 */
internal fun TypeDeclaration<*>.hasClassLombokField(): Boolean {
    return this.annotations.any {
        LombokAnnoName.lombokClassField.contains(it.nameAsString)
    }
}

internal fun TypeDeclaration<*>.fieldFromGetOrSetMethod(): List<String> {
    return this.methods
        .asSequence()
        .filter { it.isPublic }
        .filter { !it.isStatic }
        .filter { !it.isFinal }
        .map { it.nameAsString }
        .filter { it.startsWith("get") || it.startsWith("set") }
        .map { fieldNameRemovePrefix(it) }
        .toList()
}
