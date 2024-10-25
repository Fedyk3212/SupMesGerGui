plugins {
    id("java")
    id("idea")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

group = "feodorbal.client"
version = "2.5.1"

java.sourceCompatibility = JavaVersion.VERSION_11
val mainClass = "feodorbal.client.OpenChat"

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.jar {
    manifest.attributes["Main-Class"] = mainClass
    archiveBaseName = "OpenChat"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/INDEX.LIST")
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}



repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")

}

tasks.test {
    useJUnitPlatform()
}
