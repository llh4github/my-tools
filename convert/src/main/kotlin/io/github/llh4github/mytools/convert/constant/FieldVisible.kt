package io.github.llh4github.mytools.convert.constant

/**
 * 字段可见性
 *
 * Created At 2023/12/30 16:37
 * @author llh
 */
enum class FieldVisible {
    /**
     * 公开的
     */
    PUBLIC,

    /**
     * 私有的。可见性取决于类上是否有lombok注解或有没有生成setter,getter方法
     */
    PRIVATE,

    /**
     * 被lombok标记为不可访问的。
     */
    MARK_NO_ACCESS
}