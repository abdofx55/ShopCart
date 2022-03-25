package com.shopcart.utilities

import android.app.Activity
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.shopcart.R

class Tasks {

    companion object {

        @JvmStatic
        fun hideSystemBars(activity: Activity) {
            val windowInsetsController =
                ViewCompat.getWindowInsetsController(activity.window.decorView) ?: return
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // Hide both the status bar and the navigation bar
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        }

        @JvmStatic
        fun showSystemBars(activity: Activity) {
            val windowInsetsController =
                ViewCompat.getWindowInsetsController(activity.window.decorView) ?: return
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // Hide both the status bar and the navigation bar
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }

        @JvmStatic
        fun colorNavigationBar(activity: Activity) {
            // Color Navigation Bar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.navigationBarColor =
                    ContextCompat.getColor(activity, R.color.gradientStartColor)
            }
        }

        @JvmStatic
        fun defaultNavigationBar(activity: Activity) {
            // Color Navigation Bar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.navigationBarColor =
                    ContextCompat.getColor(activity, android.R.color.white)
            }
        }
    }
}