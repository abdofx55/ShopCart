package com.shopcart.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shopcart.R
import com.shopcart.data.models.User
import com.shopcart.databinding.FragmentHomeBinding
import com.shopcart.ui.adapters.CategoriesAdapter
import com.shopcart.ui.adapters.HorizontalAdapter
import com.shopcart.ui.adapters.SliderAdapter
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.Tasks.Companion.defaultNavigationBar
import com.shopcart.utilities.Tasks.Companion.showSystemBars
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var storage: FirebaseStorage
    private val viewModel: MainViewModel by viewModels()
    private var bannersRef: StorageReference? = null
    private var sliderCurrentPage = 0

    private var sliderHandler: Handler = Handler()
    private lateinit var sliderRunnable: Runnable
    private var sliderDelay = 4000
    private var user: User? = null
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var featuredAdapter: HorizontalAdapter
    private lateinit var bestAdapter: HorizontalAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        showSystemBars(requireActivity())
        defaultNavigationBar(requireActivity())

        user = viewModel.user.value
        bannersRef = storage.reference.child("banners")

        sliderAdapter = SliderAdapter()
        featuredAdapter = HorizontalAdapter(HorizontalAdapter.OnClickListener { position, item ->
            // TODO item click listener
        })
        bestAdapter = HorizontalAdapter(HorizontalAdapter.OnClickListener { position, item ->
            // TODO item click listener
        })
        categoriesAdapter = CategoriesAdapter(CategoriesAdapter.OnClickListener { position, item ->
            // TODO item click listener
        })

        binding.apply {
            homeRecyclerFeatured.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            homeRecyclerBest.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            homeRecyclerCategories.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

            homePager.adapter = sliderAdapter
            homeTabDots.setupWithViewPager(homePager, true)
            homeRecyclerCategories.adapter = categoriesAdapter
            homeRecyclerFeatured.adapter = featuredAdapter
            homeRecyclerBest.adapter = bestAdapter

            homePager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    sliderCurrentPage = position
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

            sliderRunnable = object : Runnable {
                override fun run() {
                    if (sliderCurrentPage == sliderAdapter.count - 1)
                        sliderCurrentPage = 0 else sliderCurrentPage++

                    homePager.currentItem = sliderCurrentPage
                    sliderHandler.postDelayed(this, sliderDelay.toLong())
                }
            }


//            button2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Tasks.deleteAccount(requireActivity() , firebaseAuth , firestore);
//                }
//            });

            homeImgMenu.setOnClickListener(this@HomeFragment)
            homeImgCart.setOnClickListener(this@HomeFragment)
            homeTxtFeaturedMore.setOnClickListener(this@HomeFragment)
            homeTxtBestMore.setOnClickListener(this@HomeFragment)
        }

        getData()

        return binding.root
    }

    private fun getData() {
        sliderAdapter.setList(viewModel.banners.value)
        categoriesAdapter.submitList(viewModel.categories.value)
        featuredAdapter.submitList(viewModel.featuredProducts.value)
        bestAdapter.submitList(viewModel.bestSellProducts.value)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.homeImgMenu -> {
                val action = HomeFragmentDirections.actionHomeFragmentToMenuFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.homeFragment) navController.navigate(
                    action
                )
            }

            binding.homeTxtFeaturedMore -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFeaturedFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.homeFragment) navController.navigate(
                    action
                )
            }

            binding.homeTxtBestMore -> {
                val action = HomeFragmentDirections.actionHomeFragmentToBestSellFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.homeFragment) navController.navigate(
                    action
                )
            }

            binding.homeImgCart -> {
                val action = HomeFragmentDirections.actionHomeFragmentToCartFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.homeFragment) navController.navigate(
                    action
                )
            }

        }
    }

    override fun onResume() {
        super.onResume()
        checkIfSignedIn()
        sliderHandler.postDelayed(sliderRunnable, sliderDelay.toLong())
    }

    private fun checkIfSignedIn() {
        if (firebaseAuth.currentUser == null) {
            val action = HomeFragmentDirections.actionHomeFragmentToEntryFragments()
            val navController = NavHostFragment.findNavController(this)
            val navDestination = navController.currentDestination
            if (navDestination != null && navDestination.id == R.id.homeFragment) navController.navigate(
                action
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }
}