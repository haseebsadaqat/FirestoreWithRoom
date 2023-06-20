package com.example.task2kotlin
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.task2kotlin.Retrofit.Data
import com.example.task2kotlin.databinding.ActivityMainBinding
import com.example.task2kotlin.databinding.DetailFirebaseViewBinding
import com.example.task2kotlin.databinding.FragmentFirebaseBinding
import com.google.firebase.firestore.auth.User

class RecycleAdapter : RecyclerView.Adapter<RecycleAdapter.myViewHolder>() {
    private var fireBaseList = ArrayList<UserNamesModel>()
    fun setFirebaseList(userList : List<UserNamesModel>){
        this.fireBaseList = userList as ArrayList<UserNamesModel>
        notifyDataSetChanged()
    }

    inner class myViewHolder(val binding: DetailFirebaseViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = DetailFirebaseViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return myViewHolder(binding)
    }
    override fun getItemCount(): Int {
     return fireBaseList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.detailFirebaseShow.text=fireBaseList[position].name
    }
}

