package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.shopcart.R
import com.shopcart.databinding.FragmentFeaturedBinding
import com.shopcart.ui.adapters.ProductsAdapter
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.VisualUtils.SpacingItemDecoration
import com.shopcart.utilities.VisualUtils.calculateNoOfColumns
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */

@AndroidEntryPoint
class FeaturedFragment : Fragment() {
    private lateinit var binding: FragmentFeaturedBinding
    @Inject
    lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ProductsAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_featured, container, false)

        adapter = ProductsAdapter(ProductsAdapter.OnClickListener { position, item ->
            // TODO item click listener
        })

        adapter.submitList(viewModel.featuredProducts.value)

        binding.apply {
            featuredRecycler.layoutManager = GridLayoutManager(
                activity, calculateNoOfColumns(
                    activity!!, 180f, 8
                )
            )

            featuredRecycler.addItemDecoration(
                SpacingItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.grid_spacing),
                    calculateNoOfColumns(activity!!, 180f, 8)
                )
            )

            featuredRecycler.adapter = adapter

            featuredImgBack.setOnClickListener { activity!!.onBackPressed() }
        }

        return binding.root
    }
}