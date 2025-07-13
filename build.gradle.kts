plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20230618")
    implementation(files("libs//tdlib-java.jar"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    // УКАЖИ ЗДЕСЬ ТОЧНОЕ ИМЯ КЛАССА, ГДЕ ЕСТЬ `main()`
    mainClass.set("org.userbot.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("estate-userbot")
    archiveClassifier.set("")
    archiveVersion.set(version.toString())
}
