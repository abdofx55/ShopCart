package com.shopcart.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.shopcart.data.models.Banner
import com.shopcart.data.models.Category
import com.shopcart.data.models.Product
import com.shopcart.data.models.User
import javax.inject.Inject

class Repository @Inject constructor(
    private val firebaseUser: FirebaseUser?,
    private val fireStore: FirebaseFirestore?
) {


    suspend fun getUser(): User? {
        var user: User? = null

        firebaseUser?.let {
            fireStore?.collection("users")?.document(firebaseUser.uid)?.get()
                ?.addOnSuccessListener { queryDocumentSnapshots ->
                    user = queryDocumentSnapshots.toObject(
                        User::class.java
                    )
                }
        }

        return user
    }

    suspend fun getBanners(): ArrayList<Banner>? {
        var banners: ArrayList<Banner>? = null

        fireStore?.collection("banners")?.get()
            ?.addOnSuccessListener { queryDocumentSnapshots ->
                banners = queryDocumentSnapshots.toObjects(
                    Banner::class.java
                ) as ArrayList<Banner>
            }

        return banners
    }

    suspend fun getCategories(): ArrayList<Category>? {
        var categories: ArrayList<Category>? = null

        fireStore?.collection("Categories")?.get()
            ?.addOnSuccessListener { queryDocumentSnapshots ->
                categories = queryDocumentSnapshots.toObjects(
                    Category::class.java
                ) as ArrayList<Category>
            }
        return categories
    }

    suspend fun getFeaturedProducts(): ArrayList<Product>? {
        var featuredProducts: ArrayList<Product>? = null

        fireStore?.collection("products")?.get()
            ?.addOnSuccessListener { queryDocumentSnapshots ->
                featuredProducts = queryDocumentSnapshots.toObjects(
                    Product::class.java
                ) as ArrayList<Product>
            }
        return featuredProducts
    }


    suspend fun getBestSellProducts(): ArrayList<Product>? {
        var bestSellProducts: ArrayList<Product>? = null

        fireStore?.collection("products")?.get()
            ?.addOnSuccessListener { queryDocumentSnapshots ->
                bestSellProducts = queryDocumentSnapshots.toObjects(
                    Product::class.java
                ) as ArrayList<Product>
            }
        return bestSellProducts
    }

    suspend fun getFavouriteProducts(): ArrayList<Product>? {
        var favouriteProducts: ArrayList<Product>? = null

        fireStore?.collection("products")?.get()
            ?.addOnSuccessListener { queryDocumentSnapshots ->
                favouriteProducts = queryDocumentSnapshots.toObjects(
                    Product::class.java
                ) as ArrayList<Product>
            }
        return favouriteProducts
    }
}