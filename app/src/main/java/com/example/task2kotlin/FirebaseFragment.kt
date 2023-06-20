package com.example.task2kotlin
import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2kotlin.Retrofit.ApiAdapter
import com.example.task2kotlin.databinding.FragmentFirebaseBinding
class FirebaseFragment : Fragment() {
    private var _binding: FragmentFirebaseBinding? = null
    private lateinit var viewModel: FirebaseViewModel
    private val firebaseAdapter=RecycleAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentFirebaseBinding.inflate(layoutInflater,container,false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize viewModel, Firebase and Read
        initialSetup()

        //adding and recieving data
        onFireBaseDoneListener()

    }
    override fun onPause() {
        _binding!!.progressBarFb.visibility=View.INVISIBLE
        super.onPause()
    }
    override fun onDestroy() {
        _binding!!.progressBarFb.visibility=View.INVISIBLE
        super.onDestroy()
    }

    private fun initialSetup() {

        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(activity.application)).get(FirebaseViewModel::class.java)

        viewModel.InitializeFirebase()
        _binding!!.progressBarFb.visibility=View.VISIBLE
        _binding!!.FirebaseRecycleView.layoutManager = LinearLayoutManager(activity)
        _binding!!.FirebaseRecycleView.adapter = firebaseAdapter


        viewModel.reading(CommonKeys.CollectionName)

        //Observe on Startup
        viewModel.observeFirebaseLiveData().observe(requireActivity(), Observer {
            it->
            _binding!!.progressBarFb.visibility = View.INVISIBLE
            firebaseAdapter.setFirebaseList(it)
        })

    }


   private fun onFireBaseDoneListener(){
        _binding!!.FirebaseDone.setOnClickListener(View.OnClickListener {

            _binding!!.progressBarFb.visibility=View.VISIBLE

            if(!_binding!!.textFirebase.text.isNullOrEmpty() && !_binding!!.textFirebase.text.isNullOrBlank()){
                viewModel.createDocumentWithoutAuth(_binding!!.textFirebase.text.toString().trim())

                viewModel.reading(CommonKeys.CollectionName)

                //observe when value is added to Fb
                viewModel.observeFirebaseLiveData().observe(requireActivity(), Observer { it->
                    _binding!!.progressBarFb.visibility = View.INVISIBLE
                    firebaseAdapter.setFirebaseList(it)
                })

            }else{
                _binding!!.textFirebase.setError(getString(R.string.emptyfield))
                _binding!!.progressBarFb.visibility=View.INVISIBLE
            }        })

    }

    companion object {
        fun newInstance() = FirebaseFragment()
    }



}