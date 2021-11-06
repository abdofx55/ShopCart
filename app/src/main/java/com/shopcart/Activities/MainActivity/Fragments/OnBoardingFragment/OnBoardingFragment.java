package com.shopcart.Activities.MainActivity.Fragments.OnBoardingFragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.shopcart.Utilities.Tasks;
import com.shopcart.databinding.FragmentOnBoardingBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnBoardingFragment extends Fragment implements View.OnClickListener {
    int currentPage;
    OnBoardingPagerAdapter onBoardingPagerAdapter;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == onBoardingPagerAdapter.getCount() - 1) {
                binding.onboardingBtnSkip.setVisibility(View.INVISIBLE);
                binding.onboardingImgNext.setVisibility(View.GONE);
                binding.onboardingBtnFinish.setVisibility(View.VISIBLE);

            } else {
                binding.onboardingBtnSkip.setVisibility(View.VISIBLE);
                binding.onboardingImgNext.setVisibility(View.VISIBLE);
                binding.onboardingBtnFinish.setVisibility(View.GONE);
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false);
        if (isAdded()) {
            activity = getActivity();
        }

        Tasks.hideStatusBar(activity);
        Tasks.defaultNavigationBar(activity);

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
