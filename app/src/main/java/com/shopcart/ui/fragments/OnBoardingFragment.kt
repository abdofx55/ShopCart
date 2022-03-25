package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.shopcart.R
import com.shopcart.data.models.OnBoardingItem
import com.shopcart.databinding.FragmentOnBoardingBinding
import com.shopcart.ui.adapters.OnBoardingPagerAdapter
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.Tasks.Companion.defaultNavigationBar
import com.shopcart.utilities.Tasks.Companion.hideSystemBars
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment(), View.OnClickListener {
    private val TAG = OnBoardingFragment::class.java.name

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var adapter: OnBoardingPagerAdapter
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false)

        hideSystemBars(requireActivity())
        defaultNavigationBar(requireActivity())


        adapter = OnBoardingPagerAdapter()

        submitList()


        binding.apply {
            onboardingPager.adapter = adapter
            onboardingPager.currentItem = viewModel.onBoardingCurrentPage

            TabLayoutMediator(onboardingTabDots, onboardingPager) { tab, position -> }.attach()

            onboardingPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    // save position
                    viewModel.onBoardingCurrentPage = position

                    // update UI
                    if (position == adapter.itemCount - 1) {
                        binding.onboardingBtnSkip.visibility = View.INVISIBLE
                        binding.onboardingImgNext.visibility = View.GONE
                        binding.onboardingBtnFinish.visibility = View.VISIBLE
                    } else {
                        binding.onboardingBtnSkip.visibility = View.VISIBLE
                        binding.onboardingImgNext.visibility = View.VISIBLE
                        binding.onboardingBtnFinish.visibility = View.GONE
                    }
                }

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
                // register that user has seen OnBoarding Fragment
                viewModel.onBoardingState = true
            }

            binding.onboardingImgNext -> {
                if (viewModel.onBoardingCurrentPage < adapter.itemCount) {
                    binding.onboardingPager.currentItem = viewModel.onBoardingCurrentPage + 1
                }
            }
        }
    }

    private fun submitList() {
        val list: ArrayList<OnBoardingItem> = ArrayList()

        list.add(
            OnBoardingItem(
                R.drawable.onboarding_shipping,
                R.string.onboarding_title_1,
                R.string.onboarding_text_1
            )
        )
        list.add(
            OnBoardingItem(
                R.drawable.onboarding_returns, R.string.onboarding_title_1,
                R.string.onboarding_text_1
            )
        )
        list.add(
            OnBoardingItem(
                R.drawable.onboarding_support, R.string.onboarding_title_2,
                R.string.onboarding_text_2
            )
        )
        list.add(
            OnBoardingItem(
                R.drawable.onboarding_payments, R.string.onboarding_title_3,
                R.string.onboarding_text_3
            )
        )

        adapter.submitList(list)
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
}