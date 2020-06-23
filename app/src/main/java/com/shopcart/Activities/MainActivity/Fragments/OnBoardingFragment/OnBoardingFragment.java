package com.shopcart.Activities.MainActivity.Fragments.OnBoardingFragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.shopcart.Activities.MainActivity.Fragments.SplashScreenFragment;
import com.shopcart.R;
import com.shopcart.databinding.FragmentOnBoardingBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnBoardingFragment extends Fragment implements View.OnClickListener {
    int currentPage;
    Button mSkipButton, mFinishButton;
    AppCompatImageView mNextButton;
    OnBoardingPagerAdapter onBoardingPagerAdapter;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == onBoardingPagerAdapter.getCount() - 1) {
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
    private Activity activity;
    private FragmentOnBoardingBinding binding;


    public OnBoardingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity = getActivity();
//        if (activity != null) {
//            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false);
        if (isAdded()) {
            activity = getActivity();
        }

        onBoardingPagerAdapter = new OnBoardingPagerAdapter(activity);
        binding.onboardingPager.setAdapter(onBoardingPagerAdapter);
        binding.onboardingTabDots.setupWithViewPager(binding.onboardingPager, true);
        binding.onboardingPager.addOnPageChangeListener(onPageChangeListener);

        binding.onboardingBtnSkip.setOnClickListener(this);
        binding.onboardingImgNext.setOnClickListener(this);
        binding.onboardingBtnFinish.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.onboarding_btn_skip:
            case R.id.onboarding_btn_finish:
                NavDirections action = OnBoardingFragmentDirections.actionOnBoardingFragmentToWelcomeFragment();
                NavController navController = NavHostFragment.findNavController(this);
                NavDestination navDestination = navController.getCurrentDestination();
                if (navDestination != null && navDestination.getId() == R.id.onBoardingFragment)
                    navController.navigate(action);
                // register that user has seen OnBoardingActivity activity
                registerOnBoardingState();
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
        PreferenceManager.getDefaultSharedPreferences(activity).edit()
                .putBoolean(SplashScreenFragment.ON_BOARDING_STATE_KEY, true).apply();
    }
}
