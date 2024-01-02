package io.github.llh4github.mytools.api.api

import io.github.llh4github.mytools.commons.ErrorConvention
import io.github.llh4github.mytools.commons.JsonWrapper
import io.github.llh4github.mytools.commons.NoErrorEnums

/**
 *
 * Created At 2023/12/30 22:12
 * @author llh
 */
abstract class BaseApi {

    fun <T> ok(
        data: T? = null,
        ok: ErrorConvention = NoErrorEnums.OK,
    ): JsonWrapper<T> {
        return JsonWrapper.response(ok, data)
    }

    fun <T> error(
        error: ErrorConvention,
        data: T? = null
    ): JsonWrapper<T> {
        return JsonWrapper.response(error, data)
    }
}