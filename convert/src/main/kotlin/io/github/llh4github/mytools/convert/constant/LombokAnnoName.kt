package io.github.llh4github.mytools.convert.constant


/**
 *
 * Created At 2023/12/30 16:46
 * @author llh
 */
internal object LombokAnnoName {
    /**
     * lombok注解让私有字段可访问
     */
    val lombokClassField = listOf("Data", "Getter", "Setter")

    val privateAccessLevel = listOf("NONE", "PRIVATE", "PROTECTED")

}