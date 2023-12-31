package io.github.llh4github.mytools.convert.dto

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
     * 是否有特定的
     */
    val hasDataLombok: Boolean = false,
)
