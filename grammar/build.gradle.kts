plugins {
    java
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/gen")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(rootProject)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    testImplementation("org.junit.platform:junit-platform-launcher:1.6.0")
}