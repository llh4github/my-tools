package io.github.llh4github.mytools.convert.dto

/**
 * Java 字段信息
 *
 * Created At 2023/12/30 16:30
 * @author llh
 */
data class FieldInfo(
    /**
     * 字段名
     */
    val fieldName: String,
    /**
     * 字段类型名
     */
    val typeName: String,
    /**
     * 字段类型参数
     */
    val typeParams: List<String> = emptyList(),
    /**
     * 字段注释
     */
    val doc: String? = null,
    /**
     * 字段JSON别名
     */
    val jsonAlias: List<String> = emptyList(),
)
