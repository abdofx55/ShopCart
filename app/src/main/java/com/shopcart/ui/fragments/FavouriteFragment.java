package com.shopcart.ui.fragments;


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
import com.shopcart.R;
import com.shopcart.data.Repository;
import com.shopcart.data.models.Product;
import com.shopcart.databinding.FragmentFavouriteBinding;
import com.shopcart.ui.adapters.ProductsAdapter;
import com.shopcart.utilities.VisualUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    private FragmentFavouriteBinding binding;
    private Activity activity;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<Product> list;
    private ProductsAdapter adapter;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false);


        if (isAdded()) {
            activity = getActivity();
        }

        firebaseFirestore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(activity, VisualUtils.calculateNoOfColumns(activity, 180, 8));
        binding.favouriteRecycler.setLayoutManager(layoutManager);
        binding.favouriteRecycler.addItemDecoration(new VisualUtils.SpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.grid_spacing),
                VisualUtils.calculateNoOfColumns(activity, 180, 8)));

        adapter = new ProductsAdapter(activity);
        binding.favouriteRecycler.setAdapter(adapter);
        adapter.setList(Repository.getFavouriteProducts());

        binding.favouriteImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        return binding.getRoot();
    }
}
