package io.github.llh4github.mytools.convert.constant


/**
 *
 * Created At 2023/12/30 16:42
 * @author llh
 */
object FieldTypeMap {
    /**
     * 基本类型的转换
     */
    private val basicMap = mutableMapOf<String, String>()

    /**
     * 常用集合类的名称
     */
    private val collectionNameList = listOf("List", "ArrayList", "Set", "HashSet")

    init {
        basicMap["String"] = "string"
        basicMap["Integer"] = "int"
        basicMap["int"] = "int"
        basicMap["Long"] = "int64"
        basicMap["long"] = "int64"
        basicMap["Boolean"] = "bool"
        basicMap["bool"] = "bool"
        basicMap["Date"] = "time.Time"
        basicMap["LocalDateTime"] = "time.Time"
    }

    fun typeCategory(type: String): FieldTypeCategory {
        if (basicMap.contains(type)) {
            return FieldTypeCategory.NORMAL
        }
        if (collectionNameList.contains(type)) {
            return FieldTypeCategory.COLLECTION
        }
        return FieldTypeCategory.CUSTOM
    }

    fun convertTypeName(type: String): String {
        if (basicMap.contains(type)) {
            return basicMap[type]!!
        }
        if (collectionNameList.contains(type)) {
            return "[]"
        }
        return type

    }
}