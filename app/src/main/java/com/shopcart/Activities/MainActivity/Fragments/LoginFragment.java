package com.shopcart.Activities.MainActivity.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
import com.shopcart.R;
import com.shopcart.Utilities.NetworkUtils;
import com.shopcart.databinding.FragmentLoginBinding;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // Flags to update UI
    private static final int UPDATE_UI_WHEN_LOGGING = 1;
    private static final int RESET_UI_WHEN_LOGGING_FAILED = 0;
    private Activity activity;
    private FragmentLoginBinding binding;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        if (isAdded()) {
            activity = getActivity();
        }

        fragmentManager = getChildFragmentManager();
        mAuth = FirebaseAuth.getInstance();

        binding.loginImgBack.setOnClickListener(this);
        binding.loginBtnLogin.setOnClickListener(this);
        binding.loginBtnSign.setOnClickListener(this);
        binding.loginBtnForgot.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        // Back Button
        if (v.equals(binding.loginImgBack)) {
            activity.onBackPressed();

            // Login Button
        } else if (v.equals(binding.loginBtnLogin)) {
            // first check is network connected
            if (NetworkUtils.isNetworkConnected(activity)) {
                updateUIWhenLogging(UPDATE_UI_WHEN_LOGGING);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }, 300);

            } else
                Toasty.warning(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT, true).show();

            // Sign Up Button
        } else if (v.equals(binding.loginBtnSign)) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.loginFragment)
                navController.navigate(action);


            // Forgot Password Button
        } else if (v.equals(binding.loginBtnForgot)) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment();
            NavController navController = NavHostFragment.findNavController(this);
            NavDestination navDestination = navController.getCurrentDestination();
            if (navDestination != null && navDestination.getId() == R.id.loginFragment)
                navController.navigate(action);

        }
    }

    private void login() {

        mAuth.signInWithEmailAndPassword(binding.loginLayoutBody.loginEditEmail.getText().toString().trim(),
                binding.loginLayoutBody.loginEditPassword.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (isAdded()) {
                            FirebaseUser user = authResult.getUser();
                            if (user != null && user.isEmailVerified()) {
                                // Sign in success, update UI with the signed-in user's information1
                                Toasty.success(activity, getString(R.string.login_toast_login_success), Toast.LENGTH_SHORT, true).show();
                                NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
                                NavController navController = NavHostFragment.findNavController(LoginFragment.this);
                                NavDestination navDestination = navController.getCurrentDestination();
                                if (navDestination != null && navDestination.getId() == R.id.loginFragment)
                                    navController.navigate(action);
                            } else {
                                updateUIWhenLogging(RESET_UI_WHEN_LOGGING_FAILED);
                                Toasty.info(activity, getString(R.string.login_toast_please_verify), Toast.LENGTH_LONG, true).show();
                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // First Check if the fragment still added
                if (isAdded()) {
                    // If sign in fails, display a message to the user.
                    updateUIWhenLogging(RESET_UI_WHEN_LOGGING_FAILED);
                    if (e.getLocalizedMessage() != null)
                        Toasty.error(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();
                }
            }
        });
    }

    private void updateUIWhenLogging(int flag) {
        // First Check if the fragment still added
        if (isAdded()) {
            // Second Check Flag whether update or reset UI
            if (flag == UPDATE_UI_WHEN_LOGGING) {
                // Update UI during login
                binding.loginLayoutBody.loginEditEmail.setEnabled(false);
                binding.loginLayoutBody.loginEditPassword.setEnabled(false);
                binding.loginBtnLogin.setEnabled(false);
                binding.loginBtnLogin.setText(activity.getString(R.string.login_logging));
                binding.loginSpinKit.setVisibility(View.VISIBLE);

            } else if (flag == RESET_UI_WHEN_LOGGING_FAILED) {
                // Reset UI if login failed
                binding.loginLayoutBody.loginEditEmail.setEnabled(true);
                binding.loginLayoutBody.loginEditPassword.setEnabled(true);
                binding.loginBtnLogin.setEnabled(true);
                binding.loginBtnLogin.setText(activity.getString(R.string.login_login));
                binding.loginSpinKit.setVisibility(View.INVISIBLE);
            }
        }
    }
}
