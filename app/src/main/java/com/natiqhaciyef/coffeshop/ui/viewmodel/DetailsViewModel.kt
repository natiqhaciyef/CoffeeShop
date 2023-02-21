package com.natiqhaciyef.coffeshop.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natiqhaciyef.coffeshop.data.based_datas.Sizes
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val repository: AppRepository
) : ViewModel() {
    val liveData = MutableLiveData<List<CoffeeModel>>()

    fun getAllDataFromDB() = viewModelScope.launch(Dispatchers.Main) {
        liveData.value = repository.getAllCoffee()
    }

    fun insertCoffee(coffeeModel: CoffeeModel) = viewModelScope.launch(Dispatchers.Main) {
        repository.insertCoffee(coffeeModel)
    }

    fun deleteCoffee(coffeeModel: CoffeeModel) = viewModelScope.launch(Dispatchers.Main) {
        repository.deleteCoffee(coffeeModel)
    }

    fun refleshSize() {
        for (element in Sizes.list) {
            element.isChecked = element.name.lowercase() == "small"
        }
    }

    fun insertCartCoffee(cartCoffeeModel: CartCoffeeModel) = viewModelScope.launch(Dispatchers.Main) {
        repository.insertCoffeeCart(cartCoffeeModel)
    }
}