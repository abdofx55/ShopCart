package com.shopcart.ui.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shopcart.R;
import com.shopcart.User;
import com.shopcart.Utilities.NetworkUtils;
import com.shopcart.databinding.FragmentSignUpBinding;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {
    // Flags to update UI
    private static final int UPDATE_UI_WHEN_SIGNING_IN = 1;
    private static final int RESET_UI_WHEN_SIGNING_FAILED = 0;
    private Activity activity;
    private FragmentSignUpBinding binding;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        if (isAdded()) {
            activity = getActivity();
        }

        fragmentManager = getChildFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        binding.signImgBack.setOnClickListener(this);
        binding.signBtnSign.setOnClickListener(this);
        binding.signBtnLogin.setOnClickListener(this);

        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        // Back Button
        if (v.equals(binding.signImgBack)) {
            activity.onBackPressed();
        }

        // Sign up Button
        else if (v.equals(binding.signBtnSign)) {
            if (NetworkUtils.isNetworkConnected(activity)) {
                updateUIWhenSigning(UPDATE_UI_WHEN_SIGNING_IN);
                signUp();
            } else
                Toasty.warning(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT, true).show();
        }

        // Sign In Button
        else if (v.equals(binding.signBtnLogin)) {
            NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.signUpFragment)
                navController.navigate(action);

        }

    }

    private void signUp() {
        mAuth.createUserWithEmailAndPassword(binding.signLayoutBody.signEditEmail.getText().toString().trim(),
                binding.signLayoutBody.signEditPassword.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user = authResult.getUser();
                        if (isAdded() && user != null) {
                            sendVerification(user);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // First Check if the fragment still added
                if (isAdded()) {
                    // If sign in fails, display a message to the user.
                    updateUIWhenSigning(RESET_UI_WHEN_SIGNING_FAILED);
                    if (e.getLocalizedMessage() != null)
                        Toasty.error(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();
                }
            }
        });


    }

    private void sendVerification(final FirebaseUser user) {
        if (user != null) {
            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    addUserToDatabase(user);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    user.delete();
                    if (e.getLocalizedMessage() != null)
                        Toasty.error(activity, "Failed to create a new account" + "\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG,
                                true).show();
                }
            });
        }
    }

    private void addUserToDatabase(final FirebaseUser user) {
        if (user != null) {
            // Create a new user with a name

            User newUser = new User();
            newUser.setId(user.getUid());
            newUser.setEmail(binding.signLayoutBody.signEditEmail.getText().toString().trim());
            newUser.setName(binding.signLayoutBody.signEditName.getText().toString().trim());

            // Add a new document with a user ID
            firebaseFirestore.collection("users").document(user.getUid()).set(newUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if (isAdded()) {
                                Toasty.success(activity, getString(R.string.sign_toast_sign_success), Toast.LENGTH_LONG, true).show();
                                NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
                                NavController navController = NavHostFragment.findNavController(SignUpFragment.this);
                                NavDestination navDestination = navController.getCurrentDestination();
                                if (navDestination != null && navDestination.getId() == R.id.signUpFragment)
                                    navController.navigate(action);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    user.delete();
                    if (e.getLocalizedMessage() != null)
                        Toasty.error(activity, "Failed to create a new account" + "\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG,
                                true).show();
                }
            });
        }
    }

    private void updateUIWhenSigning(int flag) {
        // First Check if the fragment still added
        if (isAdded()) {
            //Second Check Flag whether update or reset UI
            if (flag == UPDATE_UI_WHEN_SIGNING_IN) {
                // Update UI during sign up
                binding.signLayoutBody.signEditName.setEnabled(false);
                binding.signLayoutBody.signEditEmail.setEnabled(false);
                binding.signLayoutBody.signEditPassword.setEnabled(false);
                binding.signBtnSign.setEnabled(false);
                binding.signBtnSign.setText(activity.getString(R.string.sign_signing));
                binding.signSpinKit.setVisibility(View.VISIBLE);

            } else if (flag == RESET_UI_WHEN_SIGNING_FAILED) {
                // Reset UI if sign up failed
                binding.signLayoutBody.signEditName.setEnabled(true);
                binding.signLayoutBody.signEditEmail.setEnabled(true);
                binding.signLayoutBody.signEditPassword.setEnabled(true);
                binding.signBtnSign.setEnabled(true);
                binding.signBtnSign.setText(activity.getString(R.string.sign_sign));
                binding.signSpinKit.setVisibility(View.INVISIBLE);
            }
        }
    }
}
