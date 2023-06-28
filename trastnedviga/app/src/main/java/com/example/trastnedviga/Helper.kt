package com.example.trastnedviga

import com.google.firebase.storage.StorageReference

inline fun getURL(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
            .addOnSuccessListener { function(it.toString()) }
            .addOnFailureListener {
                //showToast(it.message.toString())
            }
}