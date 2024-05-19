dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":db"))
}