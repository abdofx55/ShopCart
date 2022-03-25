package com.shopcart.data

import android.content.SharedPreferences
import android.content.res.Resources
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.shopcart.R
import com.shopcart.data.models.Banner
import com.shopcart.data.models.Category
import com.shopcart.data.models.Product
import com.shopcart.data.models.User
import com.shopcart.utilities.Constants
import com.shopcart.utilities.Resource
import com.shopcart.utilities.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {
    var onBoardingState: Boolean = false
        get() {
            field = sharedPreferences.getBoolean(Constants.ON_BOARDING_STATE_KEY, false)
            return field
        }
        set(value) {
            field = value
            sharedPreferences.edit()
                .putBoolean(Constants.ON_BOARDING_STATE_KEY, value).apply()
        }

    suspend fun getProfile(): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result =
                    fireStore.collection("users").document(firebaseAuth.currentUser!!.uid).get()
                        .await().toObject<User>() as User


                Resource.Success(result)
            }
        }
    }

    suspend fun signUp(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                // Create new user
                val result =
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .await()

                // Send email verification
                result.user!!.sendEmailVerification().await()

                // Add User To Database
                result.user!!.uid.let {
                    val newUser = User(it, email)
                    fireStore.collection("users").document(it).set(newUser).await()
                }

                Resource.Success(result)
            }
        }
    }

    suspend fun signIn(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (result.user!!.isEmailVerified)
                    Resource.Success(result)
                else Resource.Error(
                    Resources.getSystem().getString(R.string.login_toast_please_verify)
                )
            }
        }
    }

    suspend fun signOut() {
        return withContext(Dispatchers.IO) {
            firebaseAuth.currentUser?.let { firebaseAuth.signOut() }
        }
    }

    suspend fun getBanners(): Resource<ArrayList<Banner>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = fireStore.collection("banners").get().await()
                    .toObjects<Banner>() as ArrayList<Banner>

                Resource.Success(result)
            }
        }
    }

    suspend fun getCategories(): Resource<ArrayList<Category>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = fireStore.collection("Categories").get().await()
                    .toObjects<Category>() as ArrayList<Category>

                Resource.Success(result)
            }
        }
    }

    suspend fun getFeaturedProducts(): Resource<ArrayList<Product>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = fireStore.collection("products")
                    .whereEqualTo("isFeatured", true)
                    .get().await()
                    .toObjects<Product>() as ArrayList<Product>

                Resource.Success(result)
            }
        }
    }

    suspend fun getBestSellProducts(): Resource<ArrayList<Product>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = fireStore.collection("products")
                    .whereEqualTo("isBestSell", true)
                    .get().await()
                    .toObjects<Product>() as ArrayList<Product>

                Resource.Success(result)
            }
        }
    }

    suspend fun getFavouriteProducts(): Resource<ArrayList<Product>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = fireStore.collection("products").get().await()
                    .toObjects<Product>() as ArrayList<Product>

                Resource.Success(result)
            }
        }
    }
}