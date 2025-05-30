import org.gradle.api.tasks.testing.logging.TestExceptionFormat

/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    checkstyle
    id("com.diffplug.spotless")
    id("com.github.spotbugs")
    idea
    jacoco
    java
    `maven-publish`
    pmd
}

configurations.implementation {
    exclude(group = "org.slf4j", module = "slf4j-simple")
}

//https://melix.github.io/blog/2021/03/version-catalogs-faq.html#_can_i_use_the_version_catalog_in_buildsrc
val libs = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")

checkstyle {
    toolVersion = "${libs.findVersion("checkstyle").get()}"
    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
}

val cleanResources by tasks.registering(Delete::class) {
    delete("build/resources")
}

val compileJava by tasks.existing(JavaCompile::class) {
    options.compilerArgs.add("-parameters")
}

idea {
    module {
        excludeDirs.addAll(files("node_modules"))
        sourceDirs.add(file("build/generated/sources/annotationProcessor/java/main"))
        generatedSourceDirs.add(file("build/generated/sources/annotationProcessor/java/main"))
    }
}

jacoco {
    toolVersion = "${libs.findVersion("jacoco").get()}"
}

tasks.withType(JacocoReport::class) {
    executionData(tasks.withType(Test::class))
    classDirectories.setFrom(sourceSets.main.get().output.classesDirs)
    sourceDirectories.setFrom(sourceSets.main.get().java.srcDirs)

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of("${libs.findVersion("java").get()}"))
    }
}

pmd {
    toolVersion = "${libs.findVersion("pmd").get()}"
    ruleSetFiles = files("${rootDir}/config/pmd/pmd-ruleset.xml")
    ruleSets()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

repositories {
    mavenLocal()

    mavenCentral()

    maven {
        url = uri("https://repo.spring.io/release")
    }

    maven {
        url = uri("https://repo.spring.io/artifactory/milestone/")
    }
}

spotbugs {
    toolVersion.set("${libs.findVersion("spotbugs").get()}")
    reportsDir.set(file("${layout.buildDirectory.get()}/reports/spotbugs"))
    excludeFilter.set(file("${rootDir}/config/spotbugs/spotbugs-exclude.xml"))

    tasks.spotbugsMain {
        reports.create("html") {
            enabled = true
            setStylesheet("fancy-hist.xsl")
        }
    }
    tasks.spotbugsTest {
        reports.create("html") {
            enabled = true
            setStylesheet("fancy-hist.xsl")
        }
    }
}

spotless {
    format("misc") {
        // define the files to apply `misc` to
        target("*.gradle", "*.md", ".gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
    }
    java {
        target("src/*/java/**/*.java")

        licenseHeader(
            "/*\n" +
                " * Copyright 2025 ByteChef\n" +
                " *\n" +
                " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                " * you may not use this file except in compliance with the License.\n" +
                " * You may obtain a copy of the License at\n" +
                " *\n" +
                " *      https://www.apache.org/licenses/LICENSE-2.0\n" +
                " *\n" +
                " * Unless required by applicable law or agreed to in writing, software\n" +
                " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                " * See the License for the specific language governing permissions and\n" +
                " * limitations under the License.\n" +
                " */\n\n"
        ).named(
            "standard"
        )
        licenseHeader(
            "/*\n" +
                " * Copyright 2016-2020 the original author or authors.\n" +
                " *\n" +
                " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                " * you may not use this file except in compliance with the License.\n" +
                " * You may obtain a copy of the License at\n" +
                " *\n" +
                " *      https://www.apache.org/licenses/LICENSE-2.0\n" +
                " *\n" +
                " * Unless required by applicable law or agreed to in writing, software\n" +
                " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                " * See the License for the specific language governing permissions and\n" +
                " * limitations under the License.\n" +
                " *\n" +
                " * Modifications copyright (C) 2025 ByteChef\n" +
                " */\n\n"
        ).named(
            "original"
        ).onlyIfContentMatches(
            "author\\sArik\\sCohen"
        )
        licenseHeader(
            "/*\n" +
                " * Copyright 2025 ByteChef\n" +
                " *\n" +
                " * Licensed under the ByteChef Enterprise license (the \"Enterprise License\");\n" +
                " * you may not use this file except in compliance with the Enterprise License.\n" +
                " */\n\n"
        ).named(
            "ee"
        ).onlyIfContentMatches(
            "version\\see\\s"
        )
        eclipse().configFile("$rootDir/config/eclipse/eclipse-code-formatter-settings.xml")
        importOrder()
        removeUnusedImports()
        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
    }
    yaml {
        target("cli/**/src/**/*.yaml", "server/**/src/**/*.yaml")

        jackson()
    }
    json {
        target("cli/**/src/**/*.json", "server/**/src/**/*.json")

        jackson()
    }
}

val spotlessCheck by tasks.existing

val check by tasks.existing {
    dependsOn(spotlessCheck)
}

val test by tasks.existing(Test::class) {
    useJUnitPlatform()
    exclude("**/*IntTest*")
    testLogging {
        events("standardOut", "skipped", "failed")
        showExceptions = true
        exceptionFormat = TestExceptionFormat.FULL
        showCauses = true
        showStackTraces = true
    }
    jvmArgs("-Djava.security.egd=file:/dev/./urandom -Xmx256m")
    // uncomment if the tests reports are not generated
    // ignoreFailures = true
    reports.html.required.set(true)

    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) { // will match the outermost suite
                val output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                val startItem = "|  "
                val endItem = "  |"
//                val repeatLength = startItem.length + output.length + endItem.length
//                println("\n" + ('-' * repeatLength) + "\n" + startItem + output + endItem + "\n" + ("-" * repeatLength))
                println("\n" + "\n" + startItem + output + endItem + "\n")
            }
        }
    })
}

val testIntegration by tasks.registering(Test::class) {
    useJUnitPlatform()
    description = "Execute integration tests."
    group = "verification"
    include("**/*IntTest*")
    testLogging {
        events("standardOut", "skipped", "failed")
        showExceptions = true
        exceptionFormat = TestExceptionFormat.FULL
        showCauses = true
        showStackTraces = true
    }
    jvmArgs("-Djava.security.egd=file:/dev/./urandom -Xmx256m")

    environment["spring.profiles.active"] = "testint"

    // uncomment if the tests reports are not generated
    // ignoreFailures = true
    reports.html.required.set(true)

    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) { // will match the outermost suite
                val output =
                    "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                val startItem = "|  "
                val endItem = "  |"
//                val repeatLength = startItem.length + output.length + endItem.length
//                println("\n" + ('-' * repeatLength) + "\n" + startItem + output + endItem + "\n" + ("-" * repeatLength))
                println("\n" + "\n" + startItem + output + endItem + "\n")
            }
        }
    })
}

check {
    dependsOn(test)
    dependsOn(testIntegration)
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.10.2")
        }
    }
}

val testReport by tasks.registering(TestReport::class) {
    destinationDirectory.set(file("${layout.buildDirectory.get()}/reports/tests"))
    testResults.from(test)
}

val testIntegrationReport by tasks.registering(TestReport::class) {
    destinationDirectory.set(file("${layout.buildDirectory.get()}/reports/tests"))
    testResults.from(testIntegration)
}
