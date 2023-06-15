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
    private val binding get() = Room_binding!!
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
        val view = binding.root
        noteAdapter = NoteAdapter()

        binding.RoomRecycleView.adapter = noteAdapter
        binding.RoomRecycleView.layoutManager = LinearLayoutManager(activity)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Observe the LiveData from the ViewModel and update the adapter
        noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
            noteAdapter.updateNotes(notes)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.addToRoom.setOnClickListener(View.OnClickListener {
            binding.progressBarRM.visibility = View.VISIBLE
            if (!binding.textRoom.text.isNullOrEmpty() || !binding.textRoom.text.isNullOrBlank()) {

                noteViewModel.insertNoteText(binding.textRoom.text.toString())
                binding.progressBarRM.visibility = View.INVISIBLE
            } else {

                Toast.makeText(activity, getString(R.string.enterabove), Toast.LENGTH_SHORT).show()
                binding.progressBarRM.visibility = View.INVISIBLE
            }

        })

    }
    override fun onPause() {
        binding.progressBarRM.visibility = View.INVISIBLE
        super.onPause()
    }


}