package com.shopcart.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shopcart.R
import com.shopcart.databinding.ActivityMainBinding
import com.shopcart.utilities.Tasks.Companion.colorNavigationBar
import com.shopcart.utilities.Tasks.Companion.hideSystemBars

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars(this)
        colorNavigationBar(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}