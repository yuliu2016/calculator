plugins {
    java
}

allprojects {
    repositories {
        mavenCentral()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    testImplementation("org.junit.platform:junit-platform-launcher:1.6.0")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.fugalang.core.calculator.Calculator"
    }
}