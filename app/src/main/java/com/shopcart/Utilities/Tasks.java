package com.shopcart.Utilities;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shopcart.R;

import es.dmoral.toasty.Toasty;

public class Tasks {

    //Hide Status Bar
    public static void hideStatusBar(Activity activity) {
        if (activity != null) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
                final View decorView = activity.getWindow().getDecorView();
                decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility(flags);
                        }
                    }
                });
            }
        }
    }

    public static void showStatusBar(Activity activity) {
        if (activity != null) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
                final View decorView = activity.getWindow().getDecorView();
                decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility(flags);
                        }
                    }
                });
            }
        }
    }

    public static void colorNavigationBar(Activity activity) {
        if (activity != null) {
            // Color Navigation Bar
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.gradientStartColor));
            }
        }
    }

    public static void defaultNavigationBar(Activity activity) {
        if (activity != null) {
            // Color Navigation Bar
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setNavigationBarColor(activity.getResources().getColor(android.R.color.white));
            }
        }
    }


    // Sign out method
    public static void signOut(Activity activity, FirebaseAuth firebaseAuth) {
        firebaseAuth.signOut();
        //Show Toast
        Toasty.success(activity, "signed out", Toast.LENGTH_SHORT, true).show();
    }


    // Delete account method
    public static void deleteAccount(Activity activity, FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // Delete user from Firestore
            firebaseFirestore.collection("users").document(user.getUid()).delete();

            // Delete user from Firebase Auth
            user.delete();

            //Show Toast
            Toasty.success(activity, "Successfully deleted this account", Toast.LENGTH_SHORT, true).show();
        }
    }
}
