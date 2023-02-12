package com.natiqhaciyef.coffeshop.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val coffeeLiveData = MutableLiveData<Resource<List<CoffeeModel>>>()
    val isLoading = MutableLiveData<Boolean>(false)

    init {
        getDataFromFirebase()
    }

    fun getDataFromFirebase(){
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
}