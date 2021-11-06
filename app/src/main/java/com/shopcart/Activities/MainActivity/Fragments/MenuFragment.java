package com.shopcart.Activities.MainActivity.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.shopcart.R;
import com.shopcart.Utilities.Tasks;
import com.shopcart.databinding.FragmentMenuBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private FragmentMenuBinding binding;
    private FragmentManager fragmentManager;
    private Activity activity;

    FirebaseAuth firebaseAuth;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        if (isAdded()) activity = getActivity();

        fragmentManager = getFragmentManager();

        firebaseAuth = FirebaseAuth.getInstance();

        binding.menuBtnHome.setOnClickListener(this);
        binding.menuBtnProfile.setOnClickListener(this);
        binding.menuBtnCart.setOnClickListener(this);
        binding.menuBtnFavourite.setOnClickListener(this);
        binding.menuBtnOrders.setOnClickListener(this);
        binding.menuBtnLanguage.setOnClickListener(this);
        binding.menuBtnSettings.setOnClickListener(this);
        binding.menuImgClose.setOnClickListener(this);
        binding.menuBtnSignOut.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.menuBtnHome)) {
            activity.onBackPressed();

        } else if (v.equals(binding.menuBtnProfile)) {
            NavDirections action = MenuFragmentDirections.actionMenuFragmentToProfileFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.menuFragment)
                navController.navigate(action);

        } else if (v.equals(binding.menuBtnCart)) {
            NavDirections action = MenuFragmentDirections.actionMenuFragmentToCartFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.menuFragment)
                navController.navigate(action);


        } else if (v.equals(binding.menuBtnFavourite)) {
            NavDirections action = MenuFragmentDirections.actionMenuFragmentToFavouriteFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.menuFragment)
                navController.navigate(action);


        } else if (v.equals(binding.menuBtnOrders)) {
            NavDirections action = MenuFragmentDirections.actionMenuFragmentToMyOrdersFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.menuFragment)
                navController.navigate(action);


        } else if (v.equals(binding.menuBtnLanguage)) {
            NavDirections action = MenuFragmentDirections.actionMenuFragmentToLanguageFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.menuFragment)
                navController.navigate(action);

        } else if (v.equals(binding.menuBtnSettings)) {
            NavDirections action = MenuFragmentDirections.actionMenuFragmentToSettingsFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.menuFragment)
                navController.navigate(action);


        }else if (v.equals(binding.menuBtnSignOut)) {
            Tasks.signOut(activity, firebaseAuth);
            activity.onBackPressed();

        } else if (v.equals(binding.menuImgClose)) {
            activity.onBackPressed();
        }
    }


}
