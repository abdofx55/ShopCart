package com.shopcart.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.shopcart.R
import com.shopcart.databinding.FragmentHomeBinding
import com.shopcart.ui.adapters.CategoriesAdapter
import com.shopcart.ui.adapters.HorizontalAdapter
import com.shopcart.ui.adapters.SliderAdapter
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()

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

        checkOnBoardingState()
        checkAuthenticationState()

        setupAdapters()

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
                    viewModel.sliderCurrentPage = position
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

            Handler(Looper.getMainLooper()).postDelayed({
                // check for last item
                if (viewModel.sliderCurrentPage == sliderAdapter.count - 1)
                    viewModel.sliderCurrentPage = 0
                else viewModel.sliderCurrentPage++

                homePager.currentItem = viewModel.sliderCurrentPage

            }, Constants.HOME_SLIDER_DELAY)


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

    private fun setupAdapters() {
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
    }

    private fun getData() {
        sliderAdapter.setList(viewModel.banners.value)
        categoriesAdapter.submitList(viewModel.categories.value)
//        featuredAdapter.submitList(viewModel.featuredProducts.value)
//        bestAdapter.submitList(viewModel.bestSellProducts.value)
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

    private fun checkOnBoardingState() {
        // Check if we need to display our OnBoarding Fragment
        if (!viewModel.onBoardingState) {
            // The user hasn't seen the OnBoardingFragment yet, so show it
            openOnBoardingFragment()
        }
    }

    private fun checkAuthenticationState() {
        // Check if the user didn't sign in
        if (viewModel.firebaseAuth.currentUser == null ||
            viewModel.firebaseAuth.currentUser?.isEmailVerified == false
        ) {
            // The user hasn't signed in , so WelcomeFragment
            openWelcomeFragment()

        }
    }

    private fun openWelcomeFragment() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToEntryFragments()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.home) navController.navigate(
            action
        )
    }

    private fun openOnBoardingFragment() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToOnBoardingFragment()
        val navController = NavHostFragment.findNavController(this)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.home) navController.navigate(
            action
        )
    }
}