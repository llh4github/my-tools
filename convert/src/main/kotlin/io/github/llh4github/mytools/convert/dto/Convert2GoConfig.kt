package io.github.llh4github.mytools.convert.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 *
 * Created At 2023/12/30 22:39
 * @author llh
 */
@Schema(title = "转换Go的配置数据")
data class Convert2GoConfig(
    @Schema(title = "输入的Java代码", description = "至少包含一个完整的类定义")
    val code: String,

    @Schema(title = "是否转换私有字段", description = "默认为true")
    val reservePrivateField: Boolean = true,

    @Schema(title = "是否展开json别名", description = "Jackson注解中定义多个别名展开为多个字段")
    val expendJsonAlias: Boolean = false,
)
