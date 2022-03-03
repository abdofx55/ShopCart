package com.shopcart.ui.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopcart.data.models.Category
import com.shopcart.databinding.CategoryLayoutBinding

class CategoriesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Category, CategoriesAdapter.ViewHolder>(MyDiffUtil) {

    // DiffUtil is a utility class that calculates the difference between two lists
    // and outputs a list of update operations that converts the first list into the second one.
    companion object MyDiffUtil : DiffUtil.ItemCallback<Category>() {

        // Checks if the two objects are the same
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        // Checks if the data between the two objects is the same
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(val binding: CategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.categoryTxtName.text = currentList[position]?.name
            Glide.with(binding.root.context).load(currentList[position]?.photoUrl)
                .into(binding.categoryImgPhoto)
            binding.categoryImgPhoto.colorFilter = PorterDuffColorFilter(
                determineColorFilter(currentList[position]?.colorFilter),
                PorterDuff.Mode.SRC_OVER
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClickListener.onClick(position, getItem(position)) }
        holder.bind(position)
    }

    class OnClickListener(val clickListener: (position: Int, item: Category) -> Unit) {
        fun onClick(position: Int, item: Category) = clickListener(position, item)
    }

    fun determineColorFilter(colorFilter: Int?): Int {
        return when (colorFilter) {
            1 -> Color.argb(100, 255, 0, 0) //Red
            2 -> Color.argb(100, 0, 255, 0) //Green
            3 -> Color.argb(100, 0, 0, 255) //Blue
            else -> Color.argb(100, 0, 0, 255)
        }
    }
}