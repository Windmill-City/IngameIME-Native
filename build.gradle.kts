@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.5.21"
}

group = "city.windmill"
version = "0.1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("osX")
        hostOs == "Linux" -> linuxX64("linux")
        isMingwX64 -> {
            mingwX64("win32") {
                val tf by compilations.getByName("main").cinterops.creating {
                    defFile(project.file("src/win32Main/resources/TextServiceFramework.def"))
                    packageName("platform.win32.tf")
                }
            }
        }
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                api("co.touchlab:kermit:0.1.9")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        when {
            hostOs == "Mac OS X" -> {
                val osXMain by getting
                val osXTest by getting
            }
            hostOs == "Linux" -> {
                val linuxMain by getting
                val linuxTest by getting
            }
            isMingwX64 -> {
                val win32Main by getting
                val win32Test by getting
            }
        }
    }
}