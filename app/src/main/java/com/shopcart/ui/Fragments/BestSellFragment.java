package com.shopcart.ui.Fragments;


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
import com.shopcart.DataRepository;
import com.shopcart.Product;
import com.shopcart.R;
import com.shopcart.Utilities.VisualUtils;
import com.shopcart.databinding.FragmentBestSellBinding;
import com.shopcart.ui.adapters.ProductsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestSellFragment extends Fragment {
    private FragmentBestSellBinding binding;
    private Activity activity;

    private FirebaseFirestore firebaseFirestore;
    private ArrayList<Product> list;
    private ProductsAdapter adapter;


    public BestSellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_best_sell, container, false);


        if (isAdded()) {
            activity = getActivity();
        }

        firebaseFirestore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(activity , VisualUtils.calculateNoOfColumns(activity , 180 , 8));
        binding.bestRecycler.setLayoutManager(layoutManager);
        binding.bestRecycler.addItemDecoration(new VisualUtils.SpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.grid_spacing),
                VisualUtils.calculateNoOfColumns(activity , 180 , 8)));

        adapter = new ProductsAdapter(activity);
        binding.bestRecycler.setAdapter(adapter);
        adapter.setList(DataRepository.getBestSellProducts());

        binding.bestImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        return binding.getRoot();
    }
}
