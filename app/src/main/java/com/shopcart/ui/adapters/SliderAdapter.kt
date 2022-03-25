package com.shopcart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopcart.data.models.Banner
import com.shopcart.databinding.SliderLayoutBinding

class SliderAdapter : ListAdapter<Banner, SliderAdapter.ViewHolder>(MyDiffUtil) {

    // DiffUtil is a utility class that calculates the difference between two lists
    // and outputs a list of update operations that converts the first list into the second one.
    companion object MyDiffUtil : DiffUtil.ItemCallback<Banner>() {


        // Checks if the two objects are the same
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

        // Checks if the data between the two objects is the same
        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }

    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(val binding: SliderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            Glide.with(binding.root.context).load(currentList[position].photoUrl)
                .into(binding.sliderImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SliderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}