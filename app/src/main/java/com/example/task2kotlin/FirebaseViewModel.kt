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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2kotlin.databinding.FragmentFirebaseBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var db: FirebaseFirestore
    var nameslist = ArrayList<UserNamesModel>()

    fun InitializeFirebase() {
        db = FirebaseFirestore.getInstance()
    }

    // Function to create a new document with an auto-generated ID
    fun createDocumentWithoutAuth(value: Any) {
        // Create a reference to the collection
        val collectionRef = db.collection("Names")
        // Create a new document with an auto-generated ID
        val documentRef = collectionRef.document()
        // Create a data object for document
        val data = hashMapOf(
            CommonKeys.fbusername to value.toString()
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
    fun reading(collectionName: String, binding: FragmentFirebaseBinding) {
        binding.FirebaseRecycleView.layoutManager = LinearLayoutManager(getApplication())
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection(collectionName)
        collectionRef.orderBy(CommonKeys.fbusername, Query.Direction.ASCENDING).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    binding.progressBarFb.visibility = View.INVISIBLE
                    Toast.makeText(getApplication(), CommonKeys.SuccessRead, Toast.LENGTH_LONG)
                        .show()
                    for (data in it.documents) {
                        var user = data.toObject(UserNamesModel::class.java)
                        if (user != null) {

                            nameslist.add(user)
                            user = null

                        } else {
                            binding.progressBarFb.visibility = View.INVISIBLE
                            Toast.makeText(
                                getApplication(),
                                CommonKeys.userNull.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    binding.FirebaseRecycleView.adapter = RecycleAdapter(nameslist)

                } else {
                    Toast.makeText(getApplication(), CommonKeys.userNull, Toast.LENGTH_LONG).show()

                    binding.progressBarFb.visibility = View.INVISIBLE
                }

            }.addOnFailureListener(OnFailureListener {
                Toast.makeText(getApplication(), CommonKeys.failedAdd, Toast.LENGTH_LONG).show()
                binding.progressBarFb.visibility = View.INVISIBLE
            })
        nameslist.clear()
    }
}