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

import com.shopcart.R;
import com.shopcart.Utilities.Tasks;
import com.shopcart.databinding.FragmentWelcomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private FragmentWelcomeBinding binding;
    private FragmentManager fragmentManager;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false);

        if (isAdded()) {
            activity = getActivity();
        }

        Tasks.showStatusBar(activity);
        Tasks.defaultNavigationBar(activity);


        binding.firstBtnLogin.setOnClickListener(this);
        binding.firstBtnSign.setOnClickListener(this);

        fragmentManager = getChildFragmentManager();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.firstBtnLogin)) {
            NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.welcomeFragment)
                navController.navigate(action);

        } else if (v.equals(binding.firstBtnSign)) {
            NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.welcomeFragment)
                navController.navigate(action);
        }
    }

}
