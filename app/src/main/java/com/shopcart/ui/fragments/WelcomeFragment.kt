package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.shopcart.R
import com.shopcart.databinding.FragmentWelcomeBinding
import com.shopcart.utilities.Tasks.Companion.defaultNavigationBar
import com.shopcart.utilities.Tasks.Companion.showSystemBars

class WelcomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        showSystemBars(requireActivity())
        defaultNavigationBar(requireActivity())

        binding.apply {
            firstBtnLogin.setOnClickListener(this@WelcomeFragment)
            firstBtnSign.setOnClickListener(this@WelcomeFragment)
        }

        return binding.root
    }

    override fun onClick(v: View) {
        when (v) {
            binding.firstBtnLogin -> {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.welcomeFragment) navController.navigate(
                    action
                )
            }

            binding.firstBtnSign -> {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.welcomeFragment) navController.navigate(
                    action
                )
            }
        }
    }
}