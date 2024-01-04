import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("kotlin-conventions")
    id("spring-conventions")
}
tasks.withType<Jar> {
    archivesName = rootProject.name
}
graalvmNative {
    binaries {
        named("main") {
            imageName.set(rootProject.name + "-" + project.version)
        }
    }
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation(project(":commons"))
    implementation(project(":convert"))
    implementation(libs.knife4j.openapi3)
}


