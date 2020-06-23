package com.shopcart.Activities.MainActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shopcart.Activities.FirstScreenActivity.FirstScreenActivity;
import com.shopcart.Activities.OnBoardingActivity.OnBoardingActivity;
import com.shopcart.DataRepository;
import com.shopcart.R;
import com.shopcart.databinding.FragmentSplashScreenBinding;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {
    public static final String ON_BOARDING_STATE_KEY = "onboarding_state";
    FirebaseUser firebaseUser;
    private Activity activity;
    private FragmentSplashScreenBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public SplashScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        if (isAdded()) {
            activity = getActivity();
        }

        if (activity != null) {
            // Hide Status Bar
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // Color Navigation Bar
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.gradientStartColor));
            }
        }

        initializeToasty();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        DataRepository.getData();


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (DataRepository.isDataDownloaded()) {
                    handler.removeCallbacks(this);
                    openNextActivity();
                } else
                    handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(runnable, 100);

        return binding.getRoot();
    }

    private void openNextActivity() {
        // Check if we need to display our OnBoardingActivity Activity
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        if (!sharedPreferences.getBoolean(
                ON_BOARDING_STATE_KEY, false)) {
            // The user hasn't seen the OnboardingSupportFragment yet, so show it
            startActivity(new Intent(activity, OnBoardingActivity.class));
        } else {
            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                startActivity(new Intent(activity, MainActivity.class));
            } else {
                startActivity(new Intent(activity, FirstScreenActivity.class));
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
