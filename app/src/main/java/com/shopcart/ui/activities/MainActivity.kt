package com.shopcart.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.shopcart.R
import com.shopcart.databinding.ActivityMainBinding
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)

        initializeToasty()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    // Configure some Toasty parameters
    private fun initializeToasty() {
        val typeface = ResourcesCompat.getFont(this, R.font.segoeui)
        if (typeface != null) {
            Toasty.Config.getInstance()
                .tintIcon(true)
                .setTextSize(15)
                .setToastTypeface(typeface)
                .allowQueue(false)
                .apply()
        } else {
            Toasty.Config.getInstance()
                .tintIcon(true)
                .setTextSize(15)
                .allowQueue(false)
                .apply()
        }
    }
}


