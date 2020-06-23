package com.shopcart.Activities.MainActivity.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.shopcart.Activities.MainActivity.ProductsAdapter;
import com.shopcart.DataRepository;
import com.shopcart.R;
import com.shopcart.Utilities.VisualUtils;
import com.shopcart.databinding.FragmentFeaturedBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturedFragment extends Fragment {
    private FragmentFeaturedBinding binding;
    private Activity activity;

    private FirebaseFirestore firebaseFirestore;
    private ProductsAdapter adapter;


    public FeaturedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_featured, container, false);


        if (isAdded()) {
            activity = getActivity();
        }

        firebaseFirestore = FirebaseFirestore.getInstance();


        GridLayoutManager layoutManager = new GridLayoutManager(activity , VisualUtils.calculateNoOfColumns(activity , 180 , 8));
        binding.featuredRecycler.setLayoutManager(layoutManager);
        binding.featuredRecycler.addItemDecoration(new VisualUtils.SpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.grid_spacing),
                VisualUtils.calculateNoOfColumns(activity , 180 , 8)));

        adapter = new ProductsAdapter(activity);
        binding.featuredRecycler.setAdapter(adapter);
        adapter.setList(DataRepository.getFeaturedProducts());

        binding.featuredImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        return binding.getRoot();
    }
}
