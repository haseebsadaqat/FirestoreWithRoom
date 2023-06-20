package com.example.task2kotlin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2kotlin.Room.NoteAdapter
import com.example.task2kotlin.Room.NoteViewModel
import com.example.task2kotlin.databinding.FragmentApiBinding
import com.example.task2kotlin.databinding.FragmentRoomDbBinding

class RoomDbFragment : Fragment() {
    private var Room_binding: FragmentRoomDbBinding? = null
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    companion object {
        fun newInstance() = RoomDbFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Room_binding = FragmentRoomDbBinding.inflate(layoutInflater, container, false)

        initialSetup()

        // Observe the LiveData from the ViewModel and update the adapter
        observeRoomLiveData()
        return Room_binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Room_binding!!.addToRoom.setOnClickListener(View.OnClickListener {
            Room_binding!!.progressBarRM.visibility = View.VISIBLE
            if (!Room_binding!!.textRoom.text.isNullOrEmpty() || !Room_binding!!.textRoom.text.isNullOrBlank()) {

                noteViewModel.insertNoteText(Room_binding!!.textRoom.text.toString())
                Room_binding!!.progressBarRM.visibility = View.INVISIBLE
            } else {

                Room_binding!!.textRoom.setError(getString(R.string.emptyfield))
                Room_binding!!.progressBarRM.visibility = View.INVISIBLE
            }

        })
    }

    override fun onPause() {
        Room_binding!!.progressBarRM.visibility = View.INVISIBLE
        super.onPause()
    }

    fun initialSetup(){
        noteAdapter = NoteAdapter()
        Room_binding!!.RoomRecycleView.adapter = noteAdapter
        Room_binding!!.RoomRecycleView.layoutManager = LinearLayoutManager(activity)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    private fun observeRoomLiveData() {
        noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
            noteAdapter.updateNotes(notes.reversed())
        }
    }





}