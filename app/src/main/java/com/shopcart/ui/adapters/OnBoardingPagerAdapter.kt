package com.shopcart.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.shopcart.R
import com.shopcart.databinding.ItemOnBoardingBinding

class OnBoardingPagerAdapter : PagerAdapter() {

    // ِArrays
    private val icons = intArrayOf(
        R.drawable.onboarding_shipping,
        R.drawable.onboarding_returns,
        R.drawable.onboarding_support,
        R.drawable.onboarding_payments
    )

    private val titles = intArrayOf(
        R.string.onboarding_title_1,
        R.string.onboarding_title_2,
        R.string.onboarding_title_3,
        R.string.onboarding_title_4
    )

    private val texts = intArrayOf(
        R.string.onboarding_text_1,
        R.string.onboarding_text_2,
        R.string.onboarding_text_3,
        R.string.onboarding_text_4
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            ItemOnBoardingBinding.inflate(LayoutInflater.from(container.context), container, false)

        binding.onboardingImgIcon.setImageResource(icons[position])
        binding.onboardingTxtTitle.text = Resources.getSystem().getString(titles[position])
        binding.onboardingTxtText.text = Resources.getSystem().getString(texts[position])

        container.addView(binding.root, position)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return icons.size
    }
}