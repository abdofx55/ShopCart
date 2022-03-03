package com.shopcart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopcart.data.models.Product
import com.shopcart.databinding.HorizontalProductLayoutBinding
import java.util.*

class HorizontalAdapter(private val onClickListener: ProductsAdapter.OnClickListener) :
    ListAdapter<Product, HorizontalAdapter.ViewHolder>(MyDiffUtil) {

    // DiffUtil is a utility class that calculates the difference between two lists
    // and outputs a list of update operations that converts the first list into the second one.
    companion object MyDiffUtil : DiffUtil.ItemCallback<Product>() {

        // Checks if the two objects are the same
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        // Checks if the data between the two objects is the same
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(val binding: HorizontalProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            Glide.with(binding.root.context).load(currentList[position]?.photosUrl?.get(0))
                .into(binding.productImgPhoto)
            binding.productTxtPrice.text =
                String.format(Locale.ENGLISH, "%.2f", currentList[position]?.price)
            binding.productTxtName.text = currentList[position]?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HorizontalProductLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClickListener.onClick(position, getItem(position)) }
        holder.bind(position)
    }

    class OnClickListener(val clickListener: (position: Int, item: Product) -> Unit) {
        fun onClick(position: Int, item: Product) = clickListener(position, item)
    }
}