package io.github.llh4github.mytools.commons

/**
 * 错误约定
 *
 * Created At 2023/12/30 17:19
 * @author llh
 */
interface ErrorConvention {
    /**
     * 响应码
     */
    val code: String

    /**
     * 响应说明
     */
    val msg: String
}
