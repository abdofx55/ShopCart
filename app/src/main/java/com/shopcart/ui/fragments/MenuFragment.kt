package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.shopcart.R
import com.shopcart.databinding.FragmentMenuBinding
import com.shopcart.utilities.Tasks.Companion.signOut
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMenuBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

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
                val action = MenuFragmentDirections.actionMenuFragmentToProfileFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
                    action
                )
            }

            binding.menuBtnCart -> {
                val action = MenuFragmentDirections.actionMenuFragmentToCartFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
                    action
                )
            }

            binding.menuBtnFavourite -> {
                val action = MenuFragmentDirections.actionMenuFragmentToFavouriteFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
                    action
                )
            }

            binding.menuBtnOrders -> {
                val action = MenuFragmentDirections.actionMenuFragmentToMyOrdersFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
                    action
                )
            }

            binding.menuBtnLanguage -> {
                val action = MenuFragmentDirections.actionMenuFragmentToLanguageFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
                    action
                )
            }

            binding.menuBtnSettings -> {
                val action = MenuFragmentDirections.actionMenuFragmentToSettingsFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.menuFragment) navController.navigate(
                    action
                )
            }

            binding.menuImgClose -> {
                signOut(requireActivity(), firebaseAuth)
                requireActivity().onBackPressed()
            }

            binding.menuBtnSignOut -> requireActivity().onBackPressed()
        }
    }
}