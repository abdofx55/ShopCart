package com.shopcart.Activities.MainActivity;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shopcart.DataRepository;
import com.shopcart.R;
import com.shopcart.User;
import com.shopcart.databinding.FragmentProfileBinding;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private FragmentProfileBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private User user;
    private FirebaseFirestore firebaseFirestore;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isDataChanged())
                binding.profileBtnSave.setVisibility(View.VISIBLE);
            else
                binding.profileBtnSave.setVisibility(View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        if (isAdded()) activity = getActivity();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = DataRepository.getUserData();

        getUserProfile();

        binding.profileImgBack.setOnClickListener(this);
        binding.profileBtnSave.setOnClickListener(this);

        return binding.getRoot();
    }

    private void getUserProfile() {
        if (user != null) {
            updateUi(user);
        }
    }


    private void updateUi(User user) {
        binding.profileBody.profileEditName.setText(user.getName() != null ? user.getName() : "");
        binding.profileBody.profileEditAddress.setText(user.getAddress() != null ? user.getAddress() : "");
        binding.profileBody.profileEditCity.setText(user.getCity() != null ? user.getCity() : "");
        binding.profileBody.profileEditGender.setText(String.valueOf(user.getGender()));
        binding.profileBody.profileEditEmail.setText(user.getEmail() != null ? user.getEmail() : "");
        binding.profileBody.profileEditMobile.setText(user.getMobile() != null ? user.getMobile() : "");

        // Add text watcher to enable save button if text changed
        binding.profileBody.profileEditName.addTextChangedListener(textWatcher);
        binding.profileBody.profileEditAddress.addTextChangedListener(textWatcher);
        binding.profileBody.profileEditCity.addTextChangedListener(textWatcher);
        binding.profileBody.profileEditGender.addTextChangedListener(textWatcher);
        binding.profileBody.profileEditEmail.addTextChangedListener(textWatcher);
        binding.profileBody.profileEditMobile.addTextChangedListener(textWatcher);
    }

    private boolean isDataChanged(){
        return !(user.getName().equals(binding.profileBody.profileEditName.getText().toString().trim()) &&
                user.getAddress().equals(binding.profileBody.profileEditAddress.getText().toString().trim()) &&
                user.getCity().equals(binding.profileBody.profileEditCity.getText().toString().trim()) &&
                String.valueOf(user.getGender()).equals(binding.profileBody.profileEditGender.getText().toString().trim()) &&
                user.getEmail().equals(binding.profileBody.profileEditEmail.getText().toString().trim()) &&
                user.getMobile().equals(binding.profileBody.profileEditMobile.getText().toString().trim())
        );
    }
    @Override
    public void onClick(View v) {
        if (v.equals(binding.profileImgBack)){
            activity.onBackPressed();

        } else if (v.equals(binding.profileBtnSave)){
            user.setName(binding.profileBody.profileEditName.getText().toString().trim());
            user.setAddress(binding.profileBody.profileEditAddress.getText().toString().trim());
            user.setCity(binding.profileBody.profileEditCity.getText().toString().trim());
            user.setGender(Integer.parseInt(binding.profileBody.profileEditGender.getText().toString().trim()));
            user.setEmail(binding.profileBody.profileEditEmail.getText().toString().trim());
            user.setMobile(binding.profileBody.profileEditMobile.getText().toString().trim());

            firebaseFirestore.collection("users").document(firebaseUser.getUid()).set(user);
            Toasty.success(activity, "Saved", Toasty.LENGTH_SHORT, true).show();
            activity.onBackPressed();
        }
    }
}
