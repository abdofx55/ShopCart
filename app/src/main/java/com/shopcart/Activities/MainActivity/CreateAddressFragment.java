package com.shopcart.Activities.MainActivity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shopcart.R;
import com.shopcart.databinding.FragmentCreateAddressBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAddressFragment extends Fragment {
    private FragmentCreateAddressBinding binding;


    public CreateAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_create_address, container, false);




        return binding.getRoot();
    }

}
