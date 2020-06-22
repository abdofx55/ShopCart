package com.shopcart.Activities.MainActivity;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.shopcart.R;
import com.shopcart.Utilities.Tasks;
import com.shopcart.databinding.FragmentMenuBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private FragmentMenuBinding binding;
    private FragmentManager fragmentManager;
    private Activity activity;

    FirebaseAuth firebaseAuth;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        if (isAdded()) activity = getActivity();

        fragmentManager = getFragmentManager();

        firebaseAuth = FirebaseAuth.getInstance();

        binding.menuBtnHome.setOnClickListener(this);
        binding.menuBtnProfile.setOnClickListener(this);
        binding.menuBtnCart.setOnClickListener(this);
        binding.menuBtnFavourite.setOnClickListener(this);
        binding.menuBtnOrders.setOnClickListener(this);
        binding.menuBtnLanguage.setOnClickListener(this);
        binding.menuBtnSettings.setOnClickListener(this);
        binding.menuImgClose.setOnClickListener(this);
        binding.menuBtnSignOut.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.menuBtnHome)) {
            activity.onBackPressed();

        } else if (v.equals(binding.menuBtnProfile)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fragment_container, new ProfileFragment()).addToBackStack(null)
                    .commit();

        } else if (v.equals(binding.menuBtnCart)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fragment_container, new CartFragment()).addToBackStack(null)
                    .commit();

        } else if (v.equals(binding.menuBtnFavourite)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fragment_container, new FavouriteFragment()).addToBackStack(null)
                    .commit();

        } else if (v.equals(binding.menuBtnOrders)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fragment_container, new MyOrdersFragment()).addToBackStack(null)
                    .commit();

        } else if (v.equals(binding.menuBtnLanguage)) {


        } else if (v.equals(binding.menuBtnSettings)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fragment_container, new PaymentFragment()).addToBackStack(null)
                    .commit();

        }else if (v.equals(binding.menuBtnSignOut)) {
            Tasks.signOut(activity , firebaseAuth);

        } else if (v.equals(binding.menuImgClose)) {
            activity.onBackPressed();
        }
    }


}
