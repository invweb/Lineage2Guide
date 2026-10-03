package com.zx_tole.lineage2_guide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.zx_tole.lineage2_guide.ui.navigation.AppNavigation
import com.zx_tole.lineage2_guide.ui.theme.Lineage2Theme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Load data from server on first launch
        lifecycleScope.launch {
            val questsRepo = getKoin().get<com.zx_tole.lineage2_guide.domain.repository.QuestsRepository>()
            val skillsRepo = getKoin().get<com.zx_tole.lineage2_guide.domain.repository.SkillsRepository>()
            val classesRepo = getKoin().get<com.zx_tole.lineage2_guide.domain.repository.ClassesRepository>()
            val npcsRepo = getKoin().get<com.zx_tole.lineage2_guide.domain.repository.NpcsRepository>()
            val itemsRepo = getKoin().get<com.zx_tole.lineage2_guide.domain.repository.ItemsRepository>()
            
            questsRepo.refreshQuests()
            skillsRepo.refreshSkills()
            classesRepo.refreshClasses()
            npcsRepo.refreshNpcs()
            itemsRepo.refreshItems()
        }
        
        setContent {
            var isDarkTheme by mutableStateOf(true)
            Lineage2Theme(darkTheme = isDarkTheme) {
                AppNavigation()
            }
        }
    }
}
