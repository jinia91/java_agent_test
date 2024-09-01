plugins {
    id("java")
}

group = "com.network"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.bytebuddy:byte-buddy:1.15.1")
    implementation("net.bytebuddy:byte-buddy-agent:1.15.1")
}

tasks.test {
    useJUnitPlatform()
}

val agentJar by tasks.registering(Jar::class) {
    archiveClassifier.set("agent")
    from(sourceSets["main"].output)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    manifest {
        attributes(
            "Premain-Class" to "LoggingJavaAgent",
            "Can-Redefine-Classes" to "true"
        )
    }
}
