package io.github.llh4github.mytools.convert

/**
 * getter,setter方法转换为属性名
 *
 * 简单处理，不一定准确
 */
internal fun fieldNameRemovePrefix(method: String): String {
    if (method.startsWith("get")) {
        return method.removePrefix("get").replaceFirstChar { it.lowercaseChar() }
    }
    if (method.startsWith("set")) {
        return method.removePrefix("set").replaceFirstChar { it.lowercaseChar() }
    }
    return method
}

internal fun uppercaseFirstChar(name: String): String {
    return name.replaceFirstChar { it.uppercaseChar() }
}
