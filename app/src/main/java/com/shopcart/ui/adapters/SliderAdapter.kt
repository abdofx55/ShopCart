package com.shopcart.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.shopcart.data.models.Banner
import com.shopcart.databinding.SliderLayoutBinding

class SliderAdapter : PagerAdapter() {
    private var list: ArrayList<Banner>? = null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            SliderLayoutBinding.inflate(LayoutInflater.from(container.context), container, false)

        list?.let {
            if (it.isNotEmpty())
                Glide.with(container.context).load(list?.get(position)?.photoUrl)
                    .into(binding.sliderImg)
        }

        container.addView(binding.root, position)

        return binding.root
    }

    fun setList(list: ArrayList<Banner>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}