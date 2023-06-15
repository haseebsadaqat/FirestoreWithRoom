package com.example.task2kotlin
import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2kotlin.databinding.FragmentFirebaseBinding
class FirebaseFragment : Fragment() {
    private var _binding: FragmentFirebaseBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = FirebaseFragment()
    }
    private lateinit var viewModel: FirebaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentFirebaseBinding.inflate(layoutInflater,container,false)

        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(activity.application)).get(FirebaseViewModel::class.java)

        viewModel.InitializeFirebase()
        binding.progressBarFb.visibility=View.VISIBLE
        viewModel.reading(CommonKeys.CollectionName,binding)

        //adding and recieving data
        binding.FirebaseDone.setOnClickListener(View.OnClickListener {
            binding.progressBarFb.visibility=View.VISIBLE
            if(!binding.textFirebase.text.isNullOrEmpty() && !binding.textFirebase.text.isNullOrBlank()){
            viewModel.createDocumentWithoutAuth(binding.textFirebase.text.toString().trim())

             viewModel.reading(CommonKeys.CollectionName,binding)

            }else{
                binding.textFirebase.setError(getString(R.string.emptyfield))
                binding.progressBarFb.visibility=View.INVISIBLE
            }        })
    }

    override fun onPause() {
        binding.progressBarFb.visibility=View.INVISIBLE
        super.onPause()
    }
    override fun onDestroy() {
        binding.progressBarFb.visibility=View.INVISIBLE
        super.onDestroy()
    }
}