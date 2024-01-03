package io.github.llh4github.mytools.convert

import io.github.llh4github.mytools.commons.AppException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 *
 * Created At 2023/12/30 23:40
 * @author llh
 */
class JavaToGoConvertInnerTest {


    @ParameterizedTest
    @CsvSource(
        value = [
            "getStr,str",
            "setStr,str",
        ]
    )
    fun `test method name`(method: String, result: String) {
        val rs = fieldNameRemovePrefix(method)
        assertEquals(result, rs)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            """
              /**
                * a
                * b  c
                */
            """,
        ]
    )
    fun `test doc`(doc: String) {
        val re = Regex("""/\*\*\n|\*/\n|\*/""")
        val re2 = Regex("""/n\* """)
        val rs = doc.trimIndent()
            .replace(re, " ")
            .replace(re2, "\n// ")
//            .removePrefix("/**")
//            .removePrefix("*/")
        println(rs)
    }

    @ParameterizedTest
    @ValueSource(strings = ["int a;", "private string name;"])
    fun `test error class str`(code: String) {
        assertThrows<AppException> { JavaCodeParserInner.parseJavaGrammar(code) }
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            """class Demo1{},false""",
            """@Data class Demo2{},true""",
            """@Setter class Demo2{},true""",
            """@Getter class Demo2{},true""",
            """@Data @Getter @Setter class Demo2{},true""",
        ]
    )
    fun `test lombok anno in class`(code: String, has: Boolean) {
        val rs = JavaCodeParserInner.parseJavaGrammar(code).types.any { it.hasClassLombokField() }
        assertEquals(has, rs)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [""" 
        class Demo{
            final static int id = 777;
            private String name;
            int age;
            LocalDateTime birthday;
        }
    """]
    )
    fun `test field list`(code: String) {
        val rs = JavaCodeParserInner.parseJavaGrammar(code.trimIndent()).types
            .flatMap { it.memberFieldNames() }
            .toList()
        assertEquals(rs, listOf("name", "age", "birthday"))
    }

}