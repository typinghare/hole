plugins {
    id("java")
}

group = "me.jameschan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.1.0-alpha1")
    implementation("org.apache.logging.log4j:log4j:3.0.0-beta1")
    implementation("com.google.guava:guava:33.0.0-jre")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Define a custom task named "runWithVersion"
tasks.register<JavaExec>("runWithVersion") {
    mainClass.set("me.jameschan.hole.Bootstrap")
    classpath = sourceSets.main.get().runtimeClasspath
    args("--version")
}

// Define a custom task named "runWithVersion"
tasks.register<JavaExec>("createNewEntry") {
    mainClass.set("me.jameschan.hole.Bootstrap")
    classpath = sourceSets.main.get().runtimeClasspath
    args("new", "name", "James")
}