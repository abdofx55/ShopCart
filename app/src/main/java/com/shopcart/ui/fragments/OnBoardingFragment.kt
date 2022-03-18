package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.shopcart.R
import com.shopcart.databinding.FragmentOnBoardingBinding
import com.shopcart.ui.adapters.OnBoardingPagerAdapter
import com.shopcart.utilities.Constants
import com.shopcart.utilities.Tasks.Companion.defaultNavigationBar
import com.shopcart.utilities.Tasks.Companion.hideSystemBars


class OnBoardingFragment : Fragment(), View.OnClickListener {
    var currentPage = 0
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var onBoardingPagerAdapter: OnBoardingPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false)

        hideSystemBars(requireActivity())
        defaultNavigationBar(requireActivity())
        onBoardingPagerAdapter = OnBoardingPagerAdapter()

        binding.apply {
            onboardingPager.adapter = onBoardingPagerAdapter
            onboardingTabDots.setupWithViewPager(binding.onboardingPager, true)

            onboardingPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if (position == onBoardingPagerAdapter.count - 1) {
                        binding.onboardingBtnSkip.visibility = View.INVISIBLE
                        binding.onboardingImgNext.visibility = View.GONE
                        binding.onboardingBtnFinish.visibility = View.VISIBLE
                    } else {
                        binding.onboardingBtnSkip.visibility = View.VISIBLE
                        binding.onboardingImgNext.visibility = View.VISIBLE
                        binding.onboardingBtnFinish.visibility = View.GONE
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

            onboardingBtnSkip.setOnClickListener(this@OnBoardingFragment)
            onboardingImgNext.setOnClickListener(this@OnBoardingFragment)
            onboardingBtnFinish.setOnClickListener(this@OnBoardingFragment)
        }

        return binding.root
    }

    override fun onClick(v: View) {
        when (v) {
            binding.onboardingBtnSkip, binding.onboardingBtnFinish -> {
                openWelcomeFragment()
                // register that user has seen OnBoardingActivity activity
                registerOnBoardingState()
            }

            binding.onboardingImgNext -> if (currentPage < onBoardingPagerAdapter.count) {
                binding.onboardingPager.currentItem = ++currentPage
            }
        }
    }

    private fun openWelcomeFragment() {
        val action =
            OnBoardingFragmentDirections.actionOnBoardingFragmentToWelcomeFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.onBoardingFragment) navController.navigate(
            action
        )
    }

    private fun registerOnBoardingState() {
        // register that user has seen OnBoardingActivity activity
        PreferenceManager.getDefaultSharedPreferences(activity!!).edit()
            .putBoolean(Constants.ON_BOARDING_STATE_KEY, true).apply()
    }
}