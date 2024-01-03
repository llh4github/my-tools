package io.github.llh4github.mytools.convert

import io.github.llh4github.mytools.convert.constant.FieldTypeMap
import io.github.llh4github.mytools.convert.constant.FieldVisible
import io.github.llh4github.mytools.convert.dto.ClassInfo
import io.github.llh4github.mytools.convert.dto.Convert2GoConfig
import io.github.llh4github.mytools.convert.dto.FieldInfo

/**
 * 转换为Go代码
 * Created At 2023/12/31 22:44
 * @author llh
 */
internal object ConvertToGo {
    fun convert(info: ClassInfo, config: Convert2GoConfig): String {
        val sb = StringBuffer()
        handleClassInfo(info, sb)
        handleFieldInfo(info, sb, config)
        return sb.append("}").toString()
    }

    private fun handleClassInfo(info: ClassInfo, sb: StringBuffer) {
        sb.append("// ${info.className} 由 ${info.className}.java类生成\n")
        info.doc?.also { sb.append("$it \n") }
        sb.append("type ${info.className} struct { \n")
    }

    private fun handleFieldInfo(
        info: ClassInfo,
        sb: StringBuffer,
        config: Convert2GoConfig
    ) {
        val fields = if (config.reservePrivateField) {
            info.publicFields + info.privateField
        } else {
            info.publicFields
        }
        fields.forEach { field ->
            if (config.expendJsonAlias) {
                handleExpendJsonAlias(sb, field)
            } else {
                handleSimpleField(sb, field, null)
                sb.append(handleJsonTag(field.jsonAlias))
            }
            sb.append("\n")
        }
    }

    private fun handleSimpleField(
        sb: StringBuffer, field: FieldInfo,
        fieldName: String? = null,
    ) {
        field.doc?.also {
            val tmp = it.removePrefix("/**")
                .removePrefix("*")
                .removeSuffix("*/")
                .removePrefix(" ")
            sb.append("\t// $tmp")
            sb.append("\n")
        }
        field.apiDoc?.also {
            sb.append("\t// ${it.removePrefix(" ")}")
            sb.append("\n")
        }

        sb.append("\t")
        if (fieldName == null) {
            if (field.finalVisible == FieldVisible.PUBLIC) {
                sb.append(uppercaseFirstChar(field.fieldName) + " ")
            } else {
                sb.append(field.fieldName + " ")
            }
        } else {
            if (field.finalVisible == FieldVisible.PUBLIC) {
                sb.append(uppercaseFirstChar(fieldName) + " ")
            } else {
                sb.append("$fieldName ")
            }
        }
        sb.append(handleType(field))
    }

    private fun handleExpendJsonAlias(sb: StringBuffer, field: FieldInfo) {
        if (field.jsonAlias.isEmpty() || field.jsonAlias.size == 1) {
            sb.append("\t")
            handleSimpleField(sb, field)
            sb.append(handleJsonTag(field.jsonAlias))
            sb.append("\n")
        } else {
            sb.append("\t")
            handleSimpleField(sb, field)
            sb.append(handleJsonTag(emptyList()))
            sb.append("\n")
            field.jsonAlias.forEach {
                sb.append("\t")
                handleSimpleField(sb, field, it)
                sb.append(handleJsonTag(listOf(it)))
                sb.append("\n")
            }
        }

    }

    private fun handleJsonTag(jsonAlias: List<String>): String {
        if (jsonAlias.isEmpty()) {
            return ""
        }
        val str = jsonAlias.joinToString { """json:"$it" """ }
        return """ `$str`"""
    }

    private fun handleType(field: FieldInfo): String {
        val typeName = FieldTypeMap.convertTypeName(field.typeName)
        if (field.typeParams.size == 1) {
            return typeName + FieldTypeMap.convertTypeName(field.typeParams[0])
        }
        if (field.typeParams.size == 2) {
            val t1 = FieldTypeMap.convertTypeName(field.typeParams[0])
            val t2 = FieldTypeMap.convertTypeName(field.typeParams[1])
            return "$typeName[$t1]$t2"
        }
        return typeName
    }

}