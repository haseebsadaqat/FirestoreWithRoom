package com.example.task2kotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.provider.Settings.System.getString
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task2kotlin.databinding.FragmentFirebaseBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var db: FirebaseFirestore
    var nameslist = ArrayList<UserNamesModel>()
    private var firebaseLiveData = MutableLiveData<List<UserNamesModel>>()

    fun InitializeFirebase() {
        db = FirebaseFirestore.getInstance()
    }

    // Function to create a new document with an auto-generated ID
    fun createDocumentWithoutAuth(value: Any) {
        // Create a reference to the collection
        val collectionRef = db.collection(CommonKeys.CollectionName)
        // Create a new document with an auto-generated ID
        val documentRef = collectionRef.document()
        // Create a data object for document
        val data = hashMapOf(
            CommonKeys.fbusername to value.toString(),
            CommonKeys.TimeStamp to FieldValue.serverTimestamp()
        )
        // Set the data to the document
        documentRef.set(data)
            .addOnSuccessListener {
                Toast.makeText(getApplication(), CommonKeys.fbread.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(getApplication(), CommonKeys.failedAdd.toString(), Toast.LENGTH_LONG)
                    .show()

            }
    }

    //fetch from firebase
    fun reading(collectionName: String) {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection(collectionName)
        collectionRef.orderBy(CommonKeys.TimeStamp, Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {

                    Toast.makeText(getApplication(), CommonKeys.SuccessRead, Toast.LENGTH_LONG)
                        .show()
                    for (data in it.documents) {
                        var user = data.toObject(UserNamesModel::class.java)
                        if (user != null) {


                            nameslist.add(user)
                            user = null

                        } else {
                            Toast.makeText(
                                getApplication(),
                                CommonKeys.userNull.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    firebaseLiveData.value=nameslist


                } else {
                    Toast.makeText(getApplication(), CommonKeys.userNull, Toast.LENGTH_LONG).show()

                }

            }.addOnFailureListener(OnFailureListener {
                Toast.makeText(getApplication(), CommonKeys.FailedRead, Toast.LENGTH_LONG).show()
            })
        nameslist.clear()
    }


fun observeFirebaseLiveData(): LiveData<List<UserNamesModel>>{


    return firebaseLiveData
}
}