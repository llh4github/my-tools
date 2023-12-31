package io.github.llh4github.mytools.api.exception

import io.github.llh4github.mytools.commons.AppException
import io.github.llh4github.mytools.commons.JsonWrapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 *
 * Created At 2023/12/30 22:21
 * @author llh
 */
@RestControllerAdvice
class AppExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(value = [AppException::class])
    fun appErrorHandler(e: AppException): JsonWrapper<Nothing> {
        logger.error(e) { "应用出错：${e.errorMsg()} " }
        return JsonWrapper.response(e.errorMsg())
    }
}