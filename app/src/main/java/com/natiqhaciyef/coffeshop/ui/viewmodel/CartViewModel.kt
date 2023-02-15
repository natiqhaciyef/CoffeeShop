package com.natiqhaciyef.coffeshop.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.data.model.Resource
import com.natiqhaciyef.coffeshop.data.repository.AppRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val repo: AppRepository
) : ViewModel() {
    val cartLiveData = MutableLiveData<Resource<List<CartCoffeeModel>>>()

    init {
        getAllCartCoffee()
    }

    private fun getAllCartCoffee() = viewModelScope.launch(Dispatchers.Main) {
        cartLiveData.value = Resource.success(repo.getAllCoffeeCart())
    }

    fun deleteCartCoffee(cartCoffeeModel: CartCoffeeModel) = viewModelScope.launch(Dispatchers.Main) {
        repo.deleteCoffeeCart(cartCoffeeModel)
    }
}