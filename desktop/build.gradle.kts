plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.compose)
}

group = 'com.zx_tole.lineage2_guide'

dependencies {
    implementation(project(":shared"))
    
    // Compose Desktop
    implementation(platform("org.jetbrains.compose:compose-bom:2025.1.0.5"))
    implementation("org.jetbrains.compose.core:compose-core")
    implementation("org.jetbrains.compose.ui:compose-ui")
    implementation("org.jetbrains.compose.components:compose-components-ui")
    implementation("org.jetbrains.compose.components:compose-components-ui-tooling-preview")
    implementation("org.jetbrains.compose.material3:compose-material3")
    implementation("org.jetbrains.compose.material.icons:compose-material-icons-extended")
    
    // Koin for Desktop
    implementation(libs.koin.core)
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}
