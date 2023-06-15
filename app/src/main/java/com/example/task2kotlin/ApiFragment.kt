package com.example.task2kotlin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2kotlin.Retrofit.ApiAdapter
import com.example.task2kotlin.databinding.FragmentApiBinding

class ApiFragment : Fragment() {
    private var Api_binding: FragmentApiBinding? = null
    private val binding get() = Api_binding!!
    private val apiAdapter=ApiAdapter()
    private var tempstring:String =CommonKeys.defalutvalue

    companion object {
        fun newInstance() = ApiFragment()
    }

    private lateinit var viewModel: ApiViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Api_binding = FragmentApiBinding.inflate(layoutInflater,container,false)

        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)

        //making progress bar visible on roomdb accessing
        binding.progressBarAP.visibility=View.VISIBLE

        prepareRecyclerView()

        viewModel = ViewModelProvider(this)[ApiViewModel::class.java]

        viewModel.GetuserData(binding.progressBarAP)

        binding.search.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

            return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                //observing if user search
                if(!newText.isNullOrEmpty()|| !newText.isNullOrBlank()){
                    tempstring=newText

                    viewModel.observeUsersLiveData(tempstring).observe(activity, Observer { it->

                        if (it.isEmpty()){
            Toast.makeText(getActivity(),getString(R.string.nothinmatched),Toast.LENGTH_SHORT).show()

                        }
                        apiAdapter.setMovieList(it)

                    })

                    return true

                }

                //observing if search is null/ not searching
                tempstring=getString(R.string.defaultvalue)
                viewModel.observeUsersLiveData(tempstring).observe(activity, Observer {
                        it-> apiAdapter.setMovieList(it)
                })

                return true
            }

        })
        //observe for if user data dont match
        viewModel.observeUsersLiveData(tempstring).observe(activity, Observer { Data ->

            if (Data.isEmpty()){
                Toast.makeText(getActivity(),getString(R.string.nothinmatched),Toast.LENGTH_SHORT).show()
            }

            apiAdapter.setMovieList(Data)

        })

    }
    //preparing recycler adapter
     fun prepareRecyclerView() {
        binding.Apirecyclerview.adapter=apiAdapter
        binding.Apirecyclerview.layoutManager=LinearLayoutManager(activity)
        }

    //make progress bar invisible
    override fun onPause() {
        binding.progressBarAP.visibility=View.INVISIBLE
        super.onPause()
    }


}