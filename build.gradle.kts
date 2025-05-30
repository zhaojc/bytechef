plugins {
    alias(libs.plugins.com.github.ben.manes.versions)
    id("com.bytechef.java-common-conventions")
    id("jacoco-report-aggregation")
    id("jvm-test-suite")
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
}

versionCatalogUpdate {
    keep {
        versions.addAll("checkstyle", "gradle-git-properties", "jackson", "jacoco", "java", "jib-gradle-plugin", "pmd", "spotbugs", "spring-ai", "spring-boot", "spring-cloud-aws", "spring-cloud-dependencies", "spring-shell")
    }
}


subprojects {
    apply(plugin = "com.bytechef.java-common-conventions")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
        implementation(rootProject.libs.com.github.spotbugs.spotbugs.annotations)

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.shell:spring-shell-dependencies:${rootProject.libs.versions.spring.shell.get()}")
        }
    }
}

reporting {
    reports {
        @Suppress("UnstableApiUsage")
        val jacocoRootReport by registering(JacocoCoverageReport::class) {
            testSuiteName = "test"

            dependencies {
                project.subprojects
                    .filter { it.plugins.findPlugin("jacoco") != null }
                    .forEach { jacocoAggregation(it) }
            }
        }
    }
}
