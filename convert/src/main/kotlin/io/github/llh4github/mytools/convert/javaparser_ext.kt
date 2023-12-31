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


/**
 * 是否被lombok注解标记为不可访问
 */
internal fun FieldDeclaration.markNoAccess(): Boolean {
    return this.annotations
        .filter { it.nameAsString == "Getter" || it.nameAsString == "Setter" }
        .map {
            when (it) {
                is SingleMemberAnnotationExpr -> {
                    it.memberValue.asStringLiteralExpr().asString() == "AccessLevel.NONE"
                }

                is NormalAnnotationExpr -> {
                    it.pairs.filter { ele -> ele.nameAsString == "value" }
                        .map { ele -> ele.value.asStringLiteralExpr().asString() == "AccessLevel.NONE" }
                        .getOrElse(0) { false }
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
