package com.zx_tole.lineage2_guide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.zx_tole.lineage2_guide.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Install splash screen
        installSplashScreen()
        
        // Set up binding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup insets
        setupWindowInsets()
        
        // Start animations
        startAnimations()
        
        // Navigate to MainActivity
        navigateToMain()
    }
    
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun startAnimations() {
        // Logo scale animation with overshoot
        binding.logoImage.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(600)
            .setInterpolator(OvershootInterpolator())
            .withEndAction {
                binding.logoImage.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(400)
                    .start()
            }
            .start()
        
        // Title fade in
        binding.titleText.alpha = 0f
        binding.titleText.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(200)
            .start()
        
        // Subtitle fade in
        binding.subtitleText.alpha = 0f
        binding.subtitleText.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(400)
            .start()
        
        // Loading bar animation
        binding.loadingBar.alpha = 0f
        binding.loadingBar.animate()
            .alpha(1f)
            .setDuration(300)
            .setStartDelay(600)
            .start()
    }
    
    private fun navigateToMain() {
        lifecycleScope.launch {
            delay(2000)
            
            // Fade out splash screen
            binding.root.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    // Start MainActivity
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
                .start()
        }
    }
}
