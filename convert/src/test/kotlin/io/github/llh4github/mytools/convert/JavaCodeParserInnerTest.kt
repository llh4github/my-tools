package io.github.llh4github.mytools.convert

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 *
 * Created At 2023/12/30 23:40
 * @author llh
 */
class JavaCodeParserInnerTest {


    @ParameterizedTest
    @CsvSource(
        value = [
            "getStr,str",
            "setStr,str",
        ]
    )
    fun `test method name`(method: String, result: String) {
        val rs = JavaCodeParserInner.methodToFieldName(method)
        assertEquals(result, rs)
    }
}