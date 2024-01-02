package io.github.llh4github.mytools.convert.dto

import io.github.llh4github.mytools.convert.constant.FieldVisible

/**
 * Java类信息
 *
 * Created At 2023/12/30 16:34
 * @author llh
 */
data class ClassInfo(
    /**
     * 类名
     */
    val className: String,
    /**
     * 类字段
     */
    val fields: List<FieldInfo>,

    /**
     * 从方法中提取的字段名
     */
    val fieldNameFromMethod: List<String> = emptyList(),
    /**
     * 类注释
     */
    val doc: String? = null,

    /**
     * 是否有特定的lombok注解
     */
    val hasDataLombok: Boolean = false,
) {
    val privateField by lazy {
        fields.filter {
            if (hasDataLombok) {
                if (it.visible == FieldVisible.MARK_NO_ACCESS) {
                    it.finalVisible = FieldVisible.PRIVATE
                    return@filter true
                }
            } else {
                if (it.visible == FieldVisible.PRIVATE && !fieldNameFromMethod.contains(it.fieldName)) {
                    it.finalVisible = FieldVisible.PRIVATE
                    return@filter true
                }
            }
            false
        }.toList()
    }
    val publicFields by lazy {
        fields.filter {
            if (hasDataLombok) {
                if (it.visible == FieldVisible.PRIVATE || it.visible == FieldVisible.PUBLIC) {
                    it.finalVisible = FieldVisible.PUBLIC
                    return@filter true
                }
            } else {
                if (fieldNameFromMethod.contains(it.fieldName) || it.visible == FieldVisible.PUBLIC) {
                    it.finalVisible = FieldVisible.PUBLIC
                    return@filter true
                }
            }
            false
        }.toList()
    }
}
