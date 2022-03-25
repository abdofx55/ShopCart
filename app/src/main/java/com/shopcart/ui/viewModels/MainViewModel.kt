package com.shopcart.ui.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.shopcart.data.Repository
import com.shopcart.data.models.Banner
import com.shopcart.data.models.Category
import com.shopcart.data.models.Product
import com.shopcart.data.models.User
import com.shopcart.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    val firebaseAuth: FirebaseAuth,
    val fireStore: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {

    var onBoardingState: Boolean = false
        get() {
            field = repository.onBoardingState
            return field
        }
        set(value) {
            field = value
            repository.onBoardingState = value
        }

    var onBoardingCurrentPage = 0
    var sliderCurrentPage = 0

    private val _authStatus = MutableLiveData<Resource<AuthResult>>()
    val authStatus: LiveData<Resource<AuthResult>> = _authStatus

    private val _signInStatus = MutableLiveData<Resource<AuthResult>>()
    val signInStatus: LiveData<Resource<AuthResult>> = _signInStatus

    private val _profile = MutableLiveData<Resource<User>>()
    val profile: LiveData<Resource<User>> = _profile

    private val _banners = MutableLiveData<Resource<ArrayList<Banner>>>()
    val banners: LiveData<Resource<ArrayList<Banner>>> = _banners

    private val _categories = MutableLiveData<Resource<ArrayList<Category>>>()
    val categories: LiveData<Resource<ArrayList<Category>>> = _categories

    private val _featuredProducts = MutableLiveData<Resource<ArrayList<Product>>>()
    val featuredProducts: LiveData<Resource<ArrayList<Product>>> = _featuredProducts

    private val _bestSellProducts = MutableLiveData<Resource<ArrayList<Product>>>()
    val bestSellProducts: LiveData<Resource<ArrayList<Product>>> = _bestSellProducts

    private val _favouriteProducts = MutableLiveData<Resource<ArrayList<Product>>>()
    val favouriteProducts: LiveData<Resource<ArrayList<Product>>> = _favouriteProducts

    init {
        getData()
    }

    fun getData() {
        getBanners()
        getCategories()
        getFeaturedProducts()
        getBestSellProducts()

        if (isSignedIn()) {
            getProfile()
            getFavouriteProducts()
        }
    }


    private fun isSignedIn(): Boolean {
        return firebaseAuth.currentUser != null &&
                firebaseAuth.currentUser?.isEmailVerified == true
    }

    fun signUp(email: String, password: String) {
        val error =
            if (email.isEmpty() || password.isEmpty()) {
                "Empty Field"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                "Not a valid Email"
            } else null

        error?.let {
            _authStatus.postValue(Resource.Error(it))
            return
        }

        _authStatus.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.Main) {
            delay(100)
            val result = repository.signUp(email = email, password = password)
            _authStatus.postValue(result)

            // get Data
            getData()
        }
    }

    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _signInStatus.postValue(Resource.Error("Empty Field"))
        } else {
            _signInStatus.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.Main) {
                delay(100)
                val loginResult = repository.signIn(email, password)
                _signInStatus.postValue(loginResult)

                // get Data
                getData()
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.signOut()
            _signInStatus.postValue(
                Resource.Error(
                    "Signed Out"
                )
            )
        }
    }

    private fun getProfile() {
        _profile.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _profile.postValue(repository.getProfile())
        }
    }

    private fun getBanners() {
        _banners.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _banners.postValue(repository.getBanners())
        }
    }

    private fun getCategories() {
        _categories.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _categories.postValue(repository.getCategories())
        }
    }

    private fun getFeaturedProducts() {
        _featuredProducts.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _featuredProducts.postValue(repository.getFeaturedProducts())
        }
    }

    private fun getBestSellProducts() {
        _bestSellProducts.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _bestSellProducts.postValue(repository.getBestSellProducts())
        }
    }

    private fun getFavouriteProducts() {
        _favouriteProducts.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _favouriteProducts.postValue(repository.getFavouriteProducts())
        }
    }
}
