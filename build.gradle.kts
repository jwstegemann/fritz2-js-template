plugins {
    kotlin("js") version "1.6.10"
    // KSP support
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"
}

repositories {
    mavenLocal()
    mavenCentral()
}

val fritz2Version = "0.14-SNAPSHOT"

//group = "my.fritz2.app"
//version = "0.0.1-SNAPSHOT"

kotlin {
    js(LEGACY) {
        browser()
    }.binaries.executable()

    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/jsMain/kotlin")
    }
}

/**
 * KSP support - start
 */
dependencies {
    implementation("dev.fritz2:core:$fritz2Version")
    add("kspJs", "dev.fritz2:lenses-annotation-processor:$fritz2Version")
}
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspKotlinJs") dependsOn("kspKotlinJs")
}
// needed to work on Apple Silicon. Should be fixed by 1.6.20 (https://youtrack.jetbrains.com/issue/KT-49109#focus=Comments-27-5259190.0-0)
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}
/**
 * KSP support - end
 */