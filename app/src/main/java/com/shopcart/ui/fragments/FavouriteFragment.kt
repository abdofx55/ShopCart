package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.shopcart.R
import com.shopcart.databinding.FragmentFavouriteBinding
import com.shopcart.ui.adapters.ProductsAdapter
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.Resource
import com.shopcart.utilities.VisualUtils.SpacingItemDecoration
import com.shopcart.utilities.VisualUtils.calculateNoOfColumns
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    private val TAG = FavouriteFragment::class.java.name

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: ProductsAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false)

        adapter = ProductsAdapter(ProductsAdapter.OnClickListener { position, item ->
            // TODO item click listener
        })

        binding.apply {
            favouriteRecycler.layoutManager = GridLayoutManager(
                requireActivity(), calculateNoOfColumns(
                    requireActivity(), 180f, 8
                )
            )

            favouriteRecycler.addItemDecoration(
                SpacingItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.grid_spacing), calculateNoOfColumns(
                        requireActivity(), 180f, 8
                    )
                )
            )

            favouriteRecycler.adapter = adapter
        }


        binding.favouriteImgBack.setOnClickListener { requireActivity().onBackPressed() }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getFavouriteList()
    }

    private fun getFavouriteList() {
        viewModel.favouriteProducts.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    // TODO Do shimmer effect
                }

                is Resource.Success -> {
                    adapter.submitList(it.data)
                }

                is Resource.Error -> {

                }
            }
        })
    }
}