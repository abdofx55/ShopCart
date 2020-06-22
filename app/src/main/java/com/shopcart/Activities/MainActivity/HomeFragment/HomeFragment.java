package com.shopcart.Activities.MainActivity.HomeFragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator.Destination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shopcart.Activities.MainActivity.BestSellFragment;
import com.shopcart.Activities.MainActivity.FeaturedFragment;
import com.shopcart.Activities.MainActivity.MenuFragment;
import com.shopcart.DataRepository;
import com.shopcart.R;
import com.shopcart.User;
import com.shopcart.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    FragmentHomeBinding binding;
    private FragmentManager fragmentManager;

    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference bannersRef;
    private User user;

    private SliderAdapter sliderAdapter;
    private HorizontalAdapter featuredAdapter;
    private HorizontalAdapter bestAdapter;
    private CategoriesAdapter categoriesAdapter;

    int sliderCurrentPage;
    Runnable sliderRunnable;
    Handler sliderHandler;
    int sliderDelay = 4000;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        if (isAdded()) {
            activity = getActivity();
        }

        fragmentManager = getParentFragmentManager();
        user = DataRepository.getUserData();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        bannersRef = storage.getReference().child("banners");

        sliderHandler = new Handler();

        LinearLayoutManager categoriesLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager featuredLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager bestLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);


        binding.homeRecyclerFeatured.setLayoutManager(featuredLayoutManager);
        binding.homeRecyclerBest.setLayoutManager(bestLayoutManager);
        binding.homeRecyclerCategories.setLayoutManager(categoriesLayoutManager);

        binding.homeImgMenu.setOnClickListener(this);
        binding.homeTxtFeaturedMore.setOnClickListener(this);
        binding.homeTxtBestMore.setOnClickListener(this);

        sliderAdapter = new SliderAdapter(activity);
        featuredAdapter = new HorizontalAdapter(activity);
        bestAdapter = new HorizontalAdapter(activity);
        categoriesAdapter = new CategoriesAdapter(activity);

        binding.homePager.setAdapter(sliderAdapter);
        binding.homeTabDots.setupWithViewPager(binding.homePager, true);
        binding.homeRecyclerCategories.setAdapter(categoriesAdapter);
        binding.homeRecyclerFeatured.setAdapter(featuredAdapter);
        binding.homeRecyclerBest.setAdapter(bestAdapter);

        getData();

        binding.homePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                sliderCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        sliderRunnable = new Runnable() {
            public void run() {
                if (sliderCurrentPage == sliderAdapter.getCount() - 1)
                    sliderCurrentPage = 0;
                else
                    sliderCurrentPage++;

                binding.homePager.setCurrentItem(sliderCurrentPage);
                sliderHandler.postDelayed(this, sliderDelay);
            }
        };


//        binding.button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tasks.deleteAccount(activity , mAuth , firebaseFirestore);
//            }
//        });



        return binding.getRoot();
    }

    private void getData() {
        sliderAdapter.setList(DataRepository.getBanners());
        categoriesAdapter.setList(DataRepository.getCategories());
        featuredAdapter.setList(DataRepository.getFeaturedProducts());
        bestAdapter.setList(DataRepository.getBestSellProducts());
    }

    @Override
    public void onClick(View v) {

        if (binding.homeImgMenu.equals(v)) {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToMenuFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.homeFragment)
                navController.navigate(action);

        }else if (binding.homeTxtFeaturedMore.equals(v)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fragment_container, new FeaturedFragment()).addToBackStack(null)
                    .commit();
        } else if (binding.homeTxtBestMore.equals(v)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fragment_container, new BestSellFragment()).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, sliderDelay);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

}
