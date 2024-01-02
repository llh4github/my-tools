package io.github.llh4github.mytools.convert

import io.github.llh4github.mytools.convert.dto.Convert2GoConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 *
 * Created At 2024/1/1 15:56
 * @author llh
 */
class ConvertToGoTest {

    @ParameterizedTest
    @ValueSource(
        strings = ["""
            @Data
       public class Demo{
        private Integer a; 
        private String b; 
        private int c; 
        @Setter(AccessLevel.NONE)
        private int d;
        @Getter(value=AccessLevel.NONE)
        private int e;
       } 
    """]
    )
    fun `test Data Anno`(code: String) {
        val cnf = Convert2GoConfig(code.trimIndent(), reservePrivateField = false)
        val rs = JavaToGoConvert.convertModel(cnf)
            .map { ConvertToGo.convert(it, cnf) }
            .toList()
        val exp = """
// Demo 
type Demo struct { 
	A int
	B string
	C int
}
        """.trimIndent()
        Assertions.assertEquals(exp, rs[0])
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["""
            @Data
       public class Demo{
        private Integer a; 
        private String b; 
        private int c; 
        @Setter(AccessLevel.NONE)
        private int d;
        @Getter(value=AccessLevel.NONE)
        private int e;
       } 
    """]
    )
    fun `test reserve private field`(code: String) {
        val cnf = Convert2GoConfig(code.trimIndent())
        val rs = JavaToGoConvert.convertModel(cnf)
            .map { ConvertToGo.convert(it, cnf) }
            .toList()
        val exp = """
// Demo 
type Demo struct { 
	A int
	B string
	C int
	d int
	e int
}
        """.trimIndent()
        Assertions.assertEquals(exp, rs[0])
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["""
            @Data
       public class Demo{
        @JsonAlias({"a1"})
        private Integer a; 
        @JsonAlias(value={"b1","b2","b3"})
        private String b; 
        private int c; 
       } 
    """]
    )
    fun `test json alias`(code: String) {
        val cnf = Convert2GoConfig(code.trimIndent(), reservePrivateField = true, expendJsonAlias = true)
        val rs = JavaToGoConvert.convertModel(cnf)
            .map { ConvertToGo.convert(it, cnf) }
            .toList()
        val exp = """
// Demo 
type Demo struct { 
		A int `json:"a1" `

		B string
		B1 string `json:"b1" `
		B2 string `json:"b2" `
		B3 string `json:"b3" `

		C int

}
        """.trimIndent()
        Assertions.assertEquals(exp, rs[0])
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["""
            @Data
       public class Demo{
        /** A-Doc */
        private Integer a; 
        /** B-Doc */
        private String b; 
        /** C-Doc */
        @ApiModelProperty("c-api-doc")
        private int c; 
       } 
    """]
    )
    fun `test field doc`(code: String) {
        val cnf = Convert2GoConfig(code.trimIndent())
        val rs = JavaToGoConvert.convertModel(cnf)
            .map { ConvertToGo.convert(it, cnf) }
            .toList()
        val exp = """
// Demo 
type Demo struct { 
	// A-Doc 
	A int
	// B-Doc 
	B string
	// C-Doc 
	// c-api-doc
	C int
}
        """.trimIndent()
        Assertions.assertEquals(exp, rs[0])
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["""
       public class Demo{
        private Integer a; 
        private String b; 
        public void getA(){}
        public void setB(){}
       } 
    """]
    )
    fun `test get set method`(code: String) {
        val cnf = Convert2GoConfig(code.trimIndent())
        val rs = JavaToGoConvert.convertModel(cnf)
            .map { ConvertToGo.convert(it, cnf) }
            .toList()
        val exp = """
// Demo 
type Demo struct { 
	A int
	B string
}
        """.trimIndent()
        Assertions.assertEquals(exp, rs[0])
    }
}