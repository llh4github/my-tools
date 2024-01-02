package io.github.llh4github.mytools.api.api

import io.github.llh4github.mytools.commons.JsonWrapper
import io.github.llh4github.mytools.convert.JavaToGoConvert
import io.github.llh4github.mytools.convert.dto.ClassInfo
import io.github.llh4github.mytools.convert.dto.Convert2GoConfig
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 *
 * Created At 2024/1/2 10:14
 * @author llh
 */
@RestController
@RequestMapping("convert/go")
@Tag(name = "将Java转换为go")
class Convert2GoApi : BaseApi() {

    @PostMapping
    @Operation(summary = "实体类的转换")
    fun convert(@RequestBody config: Convert2GoConfig): JsonWrapper<List<ClassInfo>> {
        val rs = JavaToGoConvert.convertModel(config)
        return ok(rs)
    }
}