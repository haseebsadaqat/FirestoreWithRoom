package com.example.task2kotlin.Room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2kotlin.R
import com.example.task2kotlin.Retrofit.ApiAdapter
import com.example.task2kotlin.databinding.DetailApiViewBinding
import com.example.task2kotlin.databinding.DetailRoomViewBinding

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var noteList: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = DetailRoomViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    holder.binding.detailRoomShow.setText(noteList[position].text)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun updateNotes(notes: List<Note>) {
        noteList = notes
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: DetailRoomViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}