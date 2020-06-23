package com.shopcart.Utilities;

import android.app.Activity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import es.dmoral.toasty.Toasty;

public class Tasks {

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
