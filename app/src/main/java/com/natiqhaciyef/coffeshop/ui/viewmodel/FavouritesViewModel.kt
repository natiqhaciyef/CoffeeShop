package com.natiqhaciyef.coffeshop.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.model.Resource
import com.natiqhaciyef.coffeshop.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    val repository: AppRepository
) : ViewModel() {
    val liveFavouritesData = MutableLiveData<Resource<List<CoffeeModel>>>()

    init {
        getAllFavourites()
    }

    private fun getAllFavourites() = viewModelScope.launch(Dispatchers.Main) {
        val list = repository.getAllCoffee()
        if (list.isNotEmpty())
            liveFavouritesData.value = Resource.success(list)
        else
            liveFavouritesData.value = Resource.error("Empty list",null)
    }

    fun deleteFavourite(coffeeModel: CoffeeModel) = viewModelScope.launch(Dispatchers.Main) {
        repository.deleteCoffee(coffeeModel)
    }
}