package com.natiqhaciyef.coffeshop.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.data.model.Resource
import com.natiqhaciyef.coffeshop.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val repo: AppRepository
) : ViewModel() {
    val cartModelLiveData = MutableLiveData<Resource<List<CartCoffeeModel>>>()

    init {
        getAllCartCoffee()
    }

    fun getAllCartCoffee() = viewModelScope.launch(Dispatchers.Main) {
        cartModelLiveData.value = Resource.success(repo.getAllCoffeeCart())
    }

    fun deleteCartCoffee(cartCoffeeModel: CartCoffeeModel) = viewModelScope.launch(Dispatchers.Main) {
        repo.deleteCoffeeCart(cartCoffeeModel)
    }

    fun insertCartCoffee(cartCoffeeModel: CartCoffeeModel) = viewModelScope.launch(Dispatchers.Main) {
        repo.insertCoffeeCart(cartCoffeeModel)
    }
}