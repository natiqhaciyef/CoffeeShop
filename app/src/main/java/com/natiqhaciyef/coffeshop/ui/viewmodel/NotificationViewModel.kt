package com.natiqhaciyef.coffeshop.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.natiqhaciyef.coffeshop.data.model.NotificationModel
import com.natiqhaciyef.coffeshop.data.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    val notificationLiveData = MutableLiveData<Resource<List<NotificationModel>>>()
    val isLoading = MutableLiveData(false)

    init {
        getAllNotificationFromFirebase()
    }

    private fun getAllNotificationFromFirebase() {
        val db = Firebase.firestore
        var notList = mutableListOf<NotificationModel>()
        db.collection("Notifications").addSnapshotListener { value, error ->
            if (error != null) {
                // error handling
            } else {
                if (value != null && !value.isEmpty) {
                    isLoading.value = true
                    val docs = value.documents
                    notList.clear()
                    for (doc in docs) {
                        val title = doc.get("title") as String
                        val description = doc.get("description") as String
                        val image = "https://github.com/natiqhaciyef/CoffeeShop/blob/master/Coffee%20Images/coffee_baku_logo.png?raw=true"

                        val notificationModel =
                            NotificationModel(
                                title = title,
                                image = image,
                                description = description
                            )
                        notList.add(notificationModel)
                    }

                    isLoading.value = false
                    notificationLiveData.value = Resource.success(notList)
                }
            }
        }
    }
}