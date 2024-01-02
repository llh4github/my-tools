plugins {
    id("kotlin-conventions")
    `java-library`
}
dependencies{
    implementation(project(":commons"))
    implementation("com.github.javaparser:javaparser-core:3.25.7")
}
