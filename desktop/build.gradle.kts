plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
}

group = "com.zx_tole.lineage2_guide"

kotlin {
    jvm()
    
    sourceSets {
        val jvmMain = sourceSets.getByName("jvmMain") { }
        jvmMain.dependencies {
            implementation(project(":shared"))
            
            // Compose Desktop
            implementation("org.jetbrains.compose.foundation:foundation:1.7.0")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.0")
            implementation("org.jetbrains.compose.material3:material3:1.7.0")
            
            // Koin for Desktop
            implementation(libs.koin.core)
            
            // Coroutines
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")
        }
    }
}
