package com.shopcart.Activities.FirstScreenActivity;


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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.shopcart.R;
import com.shopcart.Utilities.NetworkUtils;
import com.shopcart.databinding.FragmentForgotPasswordBinding;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    // Flags to update UI
    private static final int UPDATE_UI_WHEN_RESETTING = 1;
    private static final int RESET_UI_WHEN_RESETTING_FAILED = 0;
    private Activity activity;
    private FragmentForgotPasswordBinding binding;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false);
        if (isAdded()) {
            activity = getActivity();
        }

        fragmentManager = getFragmentManager();
        mAuth = FirebaseAuth.getInstance();

        binding.forgotImgBack.setOnClickListener(this);
        binding.forgotBtnForgot.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        // Back Button
        if (v.equals(binding.forgotImgBack)) {
            activity.onBackPressed();

            // Forgot Button
        } else if (v.equals(binding.forgotBtnForgot)) {
            // first check is network connected
            if (NetworkUtils.isNetworkConnected(activity)) {
                updateUIWhenResetting(UPDATE_UI_WHEN_RESETTING);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, 300);

            } else
                Toasty.warning(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT, true).show();
        }

    }

    private void reset() {

        mAuth.sendPasswordResetEmail(binding.forgotEditEmail.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (isAdded()) {
                            Toasty.success(activity, getString(R.string.forgot_toast_reset_success), Toast.LENGTH_SHORT, true).show();
                            activity.onBackPressed();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (isAdded()) {
                    updateUIWhenResetting(RESET_UI_WHEN_RESETTING_FAILED);
                    if (e.getLocalizedMessage() != null) {
                        Toasty.error(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });
    }

    private void updateUIWhenResetting(int flag) {
        // First Check if the fragment still added
        if (isAdded()) {
            // Second Check Flag whether update or reset UI
            if (flag == UPDATE_UI_WHEN_RESETTING) {
                // Update UI during reset password
                binding.forgotEditEmail.setEnabled(false);
                binding.forgotBtnForgot.setEnabled(false);
                binding.forgotBtnForgot.setText(activity.getString(R.string.forgot_resetting));
                binding.forgotSpinKit.setVisibility(View.VISIBLE);

            } else if (flag == RESET_UI_WHEN_RESETTING_FAILED) {
                // Reset UI if login failed
                binding.forgotEditEmail.setEnabled(true);
                binding.forgotBtnForgot.setEnabled(true);
                binding.forgotBtnForgot.setText(activity.getString(R.string.forgot_forgot));
                binding.forgotSpinKit.setVisibility(View.INVISIBLE);
            }
        }
    }
}
