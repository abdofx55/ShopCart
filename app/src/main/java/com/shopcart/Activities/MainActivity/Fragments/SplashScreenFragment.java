package com.shopcart.Activities.MainActivity.Fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shopcart.DataRepository;
import com.shopcart.R;
import com.shopcart.databinding.FragmentSplashScreenBinding;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {
    public static final String ON_BOARDING_STATE_KEY = "onboarding_state";
    private FirebaseUser firebaseUser;
    private Activity activity;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Overrided
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        FragmentSplashScreenBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false);
        if (isAdded()) {
            activity = getActivity();
        }

        initializeToasty();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        DataRepository.getData();


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (firebaseUser == null || DataRepository.isDataDownloaded()) {
                    handler.removeCallbacks(this);
                    showNextFragment();
                } else
                    handler.postDelayed(this, 100);
            }
        };

        if (firebaseUser == null)
            handler.postDelayed(runnable, 2000);
        else
            handler.postDelayed(runnable, 100);
    }

    private void showNextFragment() {
        // Check if we need to display our OnBoardingActivity Activity
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        if (!sharedPreferences.getBoolean(
                ON_BOARDING_STATE_KEY, false)) {
            // The user hasn't seen the OnBoardingFragment yet, so show it
            NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.splashScreenFragment)
                navController.navigate(action);
        } else {
            // The user has seen the OnBoardingFragment, so check if he signed in

            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                // The user has signed in , so open HomeFragment
                NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment();
                NavController navController = NavHostFragment.findNavController(this);
                NavDestination navDestination = navController.getCurrentDestination();
                if (navDestination != null && navDestination.getId() == R.id.splashScreenFragment)
                    navController.navigate(action);

            } else {
                // The user hasn't signed in , so WelcomeFragment
                NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeFragment();
                NavController navController = NavHostFragment.findNavController(this);
                NavDestination navDestination = navController.getCurrentDestination();
                if (navDestination != null && navDestination.getId() == R.id.splashScreenFragment)
                    navController.navigate(action);
            }
        }
    }

    // Configure some Toasty parameters
    private void initializeToasty() {
        Typeface typeface = ResourcesCompat.getFont(activity, R.font.segoeui);
        if (typeface != null) {
            Toasty.Config.getInstance()
                    .tintIcon(true)
                    .setTextSize(15)
                    .setToastTypeface(typeface)
                    .allowQueue(false)
                    .apply();
        } else {
            Toasty.Config.getInstance()
                    .tintIcon(true)
                    .setTextSize(15)
                    .allowQueue(false)
                    .apply();
        }
    }
}
