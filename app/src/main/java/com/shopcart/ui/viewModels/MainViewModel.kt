package com.shopcart.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.shopcart.data.Repository
import com.shopcart.data.models.Banner
import com.shopcart.data.models.Category
import com.shopcart.data.models.Product
import com.shopcart.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var sliderCurrentPage = 0

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _banners = MutableLiveData<ArrayList<Banner>>()
    val banners: LiveData<ArrayList<Banner>> = _banners

    private val _categories = MutableLiveData<ArrayList<Category>>()
    val categories: LiveData<ArrayList<Category>> = _categories

    private val _featuredProducts = MutableLiveData<ArrayList<Product>>()
    val featuredProducts: LiveData<ArrayList<Product>> = _featuredProducts

    private val _bestSellProducts = MutableLiveData<ArrayList<Product>>()
    val bestSellProducts: LiveData<ArrayList<Product>> = _bestSellProducts

    private val _favouriteProducts = MutableLiveData<ArrayList<Product>>()
    val favouriteProducts: LiveData<ArrayList<Product>> = _favouriteProducts

    init {
        getData()
    }

    fun getData() {
        getBanners()
        getCategories()
        getFeaturedProducts()
        getBestSellProducts()
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(repository.getUser())
    }

    private fun getBanners() = viewModelScope.launch {
        _banners.postValue(repository.getBanners())
    }

    private fun getCategories() = viewModelScope.launch {
        _categories.postValue(repository.getCategories())
    }

    private fun getFeaturedProducts() = viewModelScope.launch {
        _featuredProducts.postValue(repository.getFeaturedProducts())
    }

    private fun getBestSellProducts() = viewModelScope.launch {
        _bestSellProducts.postValue(repository.getBestSellProducts())
    }

    private fun getFavouriteProducts() = viewModelScope.launch {
        _favouriteProducts.postValue(repository.getFavouriteProducts())
    }
}