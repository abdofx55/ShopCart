package com.shopcart.Activities.FirstScreenActivity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shopcart.R;
import com.shopcart.databinding.FragmentWelcomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {
    private FragmentWelcomeBinding binding;
    private FragmentManager fragmentManager;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false);

        binding.firstBtnLogin.setOnClickListener(this);
        binding.firstBtnSign.setOnClickListener(this);

        fragmentManager = getFragmentManager();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.firstBtnLogin)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.first_frame_fragment_container, new LoginFragment()).addToBackStack(null)
                    .commit();
        } else if (v.equals(binding.firstBtnSign)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.first_frame_fragment_container, new SignUpFragment()).addToBackStack(null)
                    .commit();
        }
    }

}
