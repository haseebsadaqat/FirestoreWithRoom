package com.example.task2kotlin.Retrofit

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2kotlin.R
import com.example.task2kotlin.RecycleAdapter
import com.example.task2kotlin.databinding.ActivityMainBinding
import com.example.task2kotlin.databinding.DetailApiViewBinding
import com.example.task2kotlin.databinding.DetailFirebaseViewBinding
import com.example.task2kotlin.databinding.FragmentApiBinding

class ApiAdapter : RecyclerView.Adapter<ApiAdapter.ViewHolder>() {
    private var userList = ArrayList<Data>()

    fun setMovieList(userList : List<Data>){
        this.userList = userList as ArrayList<Data>
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: DetailApiViewBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DetailApiViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(userList[position].avatar)
            .into(holder.binding.userImage)
                    holder.binding.userName.text= userList[position].first_name

    }
}