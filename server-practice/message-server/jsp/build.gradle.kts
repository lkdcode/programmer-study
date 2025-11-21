plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.KAPT)
    kotlin(Kotlin.WITH_SPRING)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper")
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")
}