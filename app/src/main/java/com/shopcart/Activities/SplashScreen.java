package com.shopcart.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shopcart.Activities.FirstScreenActivity.FirstScreenActivity;
import com.shopcart.Activities.MainActivity.MainActivity;
import com.shopcart.Activities.OnBoardingActivity.OnBoardingActivity;
import com.shopcart.DataRepository;
import com.shopcart.R;
import com.shopcart.databinding.ActivitySplashScreenBinding;

import es.dmoral.toasty.Toasty;

public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    public static final String ON_BOARDING_STATE_KEY = "onboarding_state";

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Status Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Color Navigation Bar
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setNavigationBarColor(getResources().getColor(R.color.gradientStartColor));
        }

        binding = DataBindingUtil.setContentView(this , R.layout.activity_splash_screen);

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
                    finish();
                }
                else
                    handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(runnable,100);

    }

    private void openNextActivity() {
        // Check if we need to display our OnBoardingActivity Activity
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean(
                ON_BOARDING_STATE_KEY, false)) {
            // The user hasn't seen the OnboardingSupportFragment yet, so show it
            startActivity(new Intent(SplashScreen.this, OnBoardingActivity.class));
        } else {
            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashScreen.this, FirstScreenActivity.class));
            }
        }
    }

    // Configure some Toasty parameters
    private void initializeToasty() {
        Typeface typeface = ResourcesCompat.getFont(this, R.font.segoeui);
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
