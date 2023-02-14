package com.natiqhaciyef.coffeshop.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val coffeeLiveData = MutableLiveData<Resource<List<CoffeeModel>>>()
    val isLoading = MutableLiveData(false)

    init {
        getDataFromFirebase()
    }

    private fun getDataFromFirebase(){
        val db = Firebase.firestore
        val posts = ArrayList<CoffeeModel>()
        isLoading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            db.collection("Menu").addSnapshotListener{ value, error ->
                if (error != null){
                    coffeeLiveData.value = Resource.error("Selected collection name is not correct or collection is empty",null)
                }else{
                    if (value != null && !value.isEmpty){
                        val docs = value.documents
                        posts.clear()
                        for (doc in docs){
                            val id = doc.get("id").toString().toInt()
                            val name = doc.get("name") as String
                            val detail = doc.get("detail") as String
                            val image = doc.get("image") as String
                            val price = doc.get("price") as Double
                            val size = doc.get("size") as String
                            val rating = doc.get("rating") as Double
                            val category = doc.get("category") as String

                            val coffee = CoffeeModel(
                                id = id,
                                name = name,
                                detail = detail,
                                image = image,
                                price = price,
                                size = size,
                                rating = rating,
                                category = category
                            )

                            posts.add(coffee)
                        }

                        coffeeLiveData.value = Resource.success(posts)
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun categoryFilter(category: String, list: MutableList<CoffeeModel>): MutableList<CoffeeModel> {
        var customList = mutableListOf<CoffeeModel>()
        if (category.lowercase() != "all" && category.isNotEmpty() && category.lowercase() != "" && category.lowercase() != "bütün") {
            if (category.lowercase() == "hot drinks" || category.lowercase() == "isti içkilər") {
                for (element in list) {
                    if (element.category.lowercase() == "hot drinks")
                        customList.add(element)
                }
            } else if (category.lowercase() == "ice drinks" || category.lowercase() == "soyuq içkilər") {
                for (element in list) {
                    if (element.category.lowercase() == "ice drinks")
                        customList.add(element)
                }
            } else if (category.lowercase() == "bakery" || category.lowercase() == "şirniyyatlar") {
                for (element in list) {
                    if (element.category.lowercase() == "bakery")
                        customList.add(element)
                }
            } else if (category.lowercase() == "desert" || category.lowercase() == "desertlər") {
                for (element in list) {
                    if (element.category.lowercase() == "desert")
                        customList.add(element)
                }
            }
        } else {
            customList = list
        }

        return customList
    }

    fun filterByName(text: String, coffeeList: List<CoffeeModel>): ArrayList<CoffeeModel> {
        val filterList = arrayListOf<CoffeeModel>()
        for (item in coffeeList) {
            if (item.name.lowercase().contains(text.lowercase()))
                filterList.add(item)
        }
        return filterList
    }
}