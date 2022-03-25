package com.shopcart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shopcart.data.models.OnBoardingItem
import com.shopcart.databinding.ItemOnBoardingBinding

class OnBoardingPagerAdapter :
    ListAdapter<OnBoardingItem, OnBoardingPagerAdapter.ViewHolder>(MyDiffUtil) {

    // DiffUtil is a utility class that calculates the difference between two lists
    // and outputs a list of update operations that converts the first list into the second one.
    companion object MyDiffUtil : DiffUtil.ItemCallback<OnBoardingItem>() {


        // Checks if the two objects are the same
        override fun areItemsTheSame(oldItem: OnBoardingItem, newItem: OnBoardingItem): Boolean {
            return oldItem == newItem
        }

        // Checks if the data between the two objects is the same
        override fun areContentsTheSame(oldItem: OnBoardingItem, newItem: OnBoardingItem): Boolean {
            return oldItem.icon == newItem.icon
        }

    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(val binding: ItemOnBoardingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.onboardingImgIcon.setImageResource(currentList[position].icon)
            binding.onboardingTxtTitle.text =
                binding.root.resources.getString(currentList[position].title)
            binding.onboardingTxtText.text =
                binding.root.resources.getString(currentList[position].text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOnBoardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}