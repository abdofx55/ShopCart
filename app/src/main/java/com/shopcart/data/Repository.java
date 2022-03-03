package com.shopcart.data;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shopcart.data.models.Banner;
import com.shopcart.data.models.Category;
import com.shopcart.data.models.Product;
import com.shopcart.data.models.User;

import java.util.ArrayList;

public class Repository {

    private static User userData = null;
    private static ArrayList<Banner> banners = null;
    private static ArrayList<Category> categories = null;
    private static ArrayList<Product> featuredProducts = null;
    private static ArrayList<Product> bestSellProducts = null;
    private static ArrayList<Product> favouriteProducts = null;

    private Repository() {
    }

    public static boolean isDataDownloaded() {
        return banners != null && categories != null && featuredProducts != null && bestSellProducts != null && favouriteProducts != null;
    }

    public static void getData() {
        getUserData();
        getBanners();
        getCategories();
        getFeaturedProducts();
        getBestSellProducts();
        getFavouriteProducts();
    }

    public static User getUserData() {
        if (userData == null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

            if (firebaseUser != null) {
                firebaseFirestore.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userData = documentSnapshot.toObject(User.class);
                    }
                });
            }
        }
        return userData;
    }

    public static ArrayList<Banner> getBanners() {
        if (banners == null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

            if (firebaseUser != null) {
                firebaseFirestore.collection("banners").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        banners = (ArrayList<Banner>) queryDocumentSnapshots.toObjects(Banner.class);
                    }
                });
            }
        }
        return banners;
    }


    public static ArrayList<Category> getCategories() {
        if (categories == null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            if (firebaseUser != null) {
                firebaseFirestore.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        categories = (ArrayList<Category>) queryDocumentSnapshots.toObjects(Category.class);
                    }
                });
            }
        }
        return categories;
    }

    public static ArrayList<Product> getFeaturedProducts() {
        if (featuredProducts == null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            if (firebaseUser != null) {
                firebaseFirestore.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        featuredProducts = (ArrayList<Product>) queryDocumentSnapshots.toObjects(Product.class);
                    }
                });
            }
        }
        return featuredProducts;
    }

    public static ArrayList<Product> getBestSellProducts() {
        if (featuredProducts == null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            if (firebaseUser != null) {
                firebaseFirestore.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        bestSellProducts = (ArrayList<Product>) queryDocumentSnapshots.toObjects(Product.class);
                    }
                });
            }
        }
        return bestSellProducts;
    }

    public static ArrayList<Product> getFavouriteProducts() {
        if (favouriteProducts == null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            if (firebaseUser != null) {
                firebaseFirestore.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        favouriteProducts = (ArrayList<Product>) queryDocumentSnapshots.toObjects(Product.class);
                    }
                });
            }
        }
        return favouriteProducts;
    }
}
