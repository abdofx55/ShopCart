package com.shopcart.Activities.OnBoardingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.shopcart.Activities.FirstScreenActivity.FirstScreenActivity;
import com.shopcart.Activities.MainActivity.SplashScreenFragment;
import com.shopcart.R;
import com.shopcart.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOnBoardingBinding binding;
    int currentPage;
    Button mSkipButton , mFinishButton;
    AppCompatImageView mNextButton;
    OnBoardingPagerAdapter onBoardingPagerAdapter;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == onBoardingPagerAdapter.getCount()-1){
                mSkipButton.setVisibility(View.INVISIBLE);
                mNextButton.setVisibility(View.GONE);
                mFinishButton.setVisibility(View.VISIBLE);

            } else {
                mSkipButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.VISIBLE);
                mFinishButton.setVisibility(View.GONE);
            }
        }


        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this , R.layout.activity_on_boarding);

        mSkipButton = findViewById(R.id.onboarding_btn_skip);
        mNextButton = findViewById(R.id.onboarding_img_next);
        mFinishButton = findViewById(R.id.onboarding_btn_finish);


        onBoardingPagerAdapter = new OnBoardingPagerAdapter(this);
        binding.onboardingPager.setAdapter(onBoardingPagerAdapter);
        binding.onboardingTabDots.setupWithViewPager(binding.onboardingPager , true);
        binding.onboardingPager.addOnPageChangeListener(onPageChangeListener);

        mSkipButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mFinishButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.onboarding_btn_skip :
            case R.id.onboarding_btn_finish:
                startActivity(new Intent(OnBoardingActivity.this , FirstScreenActivity.class));
                // register that user has seen OnBoardingActivity activity
                registerOnBoardingState();
                finish();
                break;
            case R.id.onboarding_img_next:
                if (currentPage < onBoardingPagerAdapter.getCount()) {
                    binding.onboardingPager.setCurrentItem(++currentPage);
                }
                break;
        }
    }

    private void registerOnBoardingState() {
        // register that user has seen OnBoardingActivity activity
        PreferenceManager.getDefaultSharedPreferences(OnBoardingActivity.this).edit()
                .putBoolean(SplashScreenFragment.ON_BOARDING_STATE_KEY, true).apply();
    }
}
