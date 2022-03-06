package com.shopcart.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.shopcart.R
import com.shopcart.databinding.FragmentBestSellBinding
import com.shopcart.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentBestSellBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()

    companion object {
        const val ON_BOARDING_STATE_KEY = "onBoarding_state"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)

        initializeToasty()

        viewModel.user
        viewModel.banners
        viewModel.categories
        viewModel.featuredProducts
        viewModel.bestSellProducts
        viewModel.favouriteProducts

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                if (firebaseAuth.currentUser == null) {
                    handler.removeCallbacks(this)
                    showNextFragment()
                } else handler.postDelayed(this, 100)
            }
        }

        if (firebaseAuth.currentUser == null) handler.postDelayed(
            runnable,
            2000
        ) else handler.postDelayed(
            runnable,
            100
        )
    }

    private fun showNextFragment() {
        // Check if we need to display our OnBoardingActivity Activity
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            requireContext()
        )

        if (!sharedPreferences.getBoolean(
                ON_BOARDING_STATE_KEY, false
            )
        ) {
            // The user hasn't seen the OnBoardingFragment yet, so show it
            val action =
                SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment()
            val navController = NavHostFragment.findNavController(this)
            val navDestination = navController.currentDestination
            if (navDestination != null && navDestination.id == R.id.splashScreenFragment) navController.navigate(
                action
            )
        } else {
            // The user has seen the OnBoardingFragment, so check if he signed in
            if (firebaseAuth.currentUser?.isEmailVerified == true) {
                // The user has signed in , so open HomeFragment
                val action =
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.splashScreenFragment) navController.navigate(
                    action
                )
            } else {
                // The user hasn't signed in , so WelcomeFragment
                val action =
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.splashScreenFragment) navController.navigate(
                    action
                )
            }
        }
    }

    // Configure some Toasty parameters
    private fun initializeToasty() {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.segoeui)
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