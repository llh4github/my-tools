package io.github.llh4github.mytools.commons


/**
 * 应用通用异常
 *
 * Created At 2023/12/30 17:16
 * @author llh
 */
class AppException(val error: ErrorConvention, cause: Throwable? = null) : RuntimeException(error.msg, cause) {

}