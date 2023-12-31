package io.github.llh4github.mytools.convert

import io.github.llh4github.mytools.convert.dto.ClassInfo
import io.github.llh4github.mytools.convert.dto.Convert2GoConfig
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 *
 * Created At 2023/12/30 16:44
 * @author llh
 */
object JavaCodeParser {
    private val logger = KotlinLogging.logger {}
    fun parse(config: Convert2GoConfig): List<ClassInfo> {
        return JavaCodeParserInner.parseJavaGrammar(config.code)
            .types.map { JavaCodeParserInner.parseJavaClass(it) }
            .toList()
    }


}