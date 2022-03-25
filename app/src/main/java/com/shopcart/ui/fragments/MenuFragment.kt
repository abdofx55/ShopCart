package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.shopcart.R
import com.shopcart.databinding.FragmentMenuBinding
import com.shopcart.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

private const val TAG = "Menu Fragment"

@AndroidEntryPoint
class MenuFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        binding.apply {
            menuBtnHome.setOnClickListener(this@MenuFragment)
            menuBtnProfile.setOnClickListener(this@MenuFragment)
            menuBtnCart.setOnClickListener(this@MenuFragment)
            menuBtnFavourite.setOnClickListener(this@MenuFragment)
            menuBtnOrders.setOnClickListener(this@MenuFragment)
            menuBtnLanguage.setOnClickListener(this@MenuFragment)
            menuBtnSettings.setOnClickListener(this@MenuFragment)
            menuImgClose.setOnClickListener(this@MenuFragment)
            menuBtnSignOut.setOnClickListener(this@MenuFragment)
        }

        return binding.root
    }

    override fun onClick(v: View) {
        when (v) {
            binding.menuBtnHome -> requireActivity().onBackPressed()

            binding.menuBtnProfile -> {
                openProfileFragment()
            }

            binding.menuBtnCart -> {
                openCartFragment()
            }

            binding.menuBtnFavourite -> {
                openFavouriteFragment()
            }

            binding.menuBtnOrders -> {
                openMyOrdersFragment()
            }

            binding.menuBtnLanguage -> {
                openLanguageFragment()
            }

            binding.menuBtnSettings -> {
                openSettingsFragment()
            }

            binding.menuBtnSignOut -> {
                viewModel.signOut()
                //Show Toast
                Toasty.success(activity!!, "signed out", Toast.LENGTH_SHORT, true).show()
                openWelcomeFragment()

            }
            binding.menuImgClose ->
                requireActivity().onBackPressed()
        }
    }


    private fun openProfileFragment() {
        val action = MenuFragmentDirections.actionMenuFragmentToProfileFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
            action
        )
    }

    private fun openCartFragment() {
        val action = MenuFragmentDirections.actionMenuFragmentToCartFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
            action
        )
    }

    private fun openFavouriteFragment() {
        val action = MenuFragmentDirections.actionMenuFragmentToFavouriteFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
            action
        )
    }

    private fun openMyOrdersFragment() {
        val action = MenuFragmentDirections.actionMenuFragmentToMyOrdersFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
            action
        )
    }

    private fun openLanguageFragment() {
        val action = MenuFragmentDirections.actionMenuFragmentToLanguageFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
            action
        )
    }

    private fun openSettingsFragment() {
        val action = MenuFragmentDirections.actionMenuFragmentToSettingsFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
            action
        )
    }

    private fun openWelcomeFragment() {
        val action = MenuFragmentDirections.actionMenuFragmentToEntryFragments()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
            action
        )
    }

}