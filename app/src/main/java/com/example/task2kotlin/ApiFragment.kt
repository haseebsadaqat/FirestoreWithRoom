package com.example.task2kotlin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2kotlin.Retrofit.ApiAdapter
import com.example.task2kotlin.databinding.FragmentApiBinding

class ApiFragment : Fragment() {
    private var Api_binding: FragmentApiBinding? = null
    private val apiAdapter=ApiAdapter()
    private var tempstring:String =CommonKeys.defalutvalue
    lateinit var activity : LifecycleOwner
    companion object {
        fun newInstance() = ApiFragment()
    }

    private lateinit var viewModel: ApiViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Api_binding = FragmentApiBinding.inflate(layoutInflater,container,false)
        return Api_binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetup()

       var sucesOrFailure=viewModel.getUserData()

        if(sucesOrFailure==true){
            Api_binding!!.progressBarAP.visibility= View.INVISIBLE
        }
        //Observe if user data matches
        onObserveQuerySearch()
        //observe for if user data dont match
        onObserveIfNotMatch()
    }
    private fun onObserveIfNotMatch() {
        viewModel.observeUsersLiveData(tempstring).observe(activity, Observer { Data ->

            if (Data.isEmpty()){
                Toast.makeText(getActivity(),getString(R.string.nothinmatched),Toast.LENGTH_SHORT).show()
            }

            apiAdapter.setMovieList(Data)

        })
    }

    override fun onPause() {
        Api_binding!!.progressBarAP.visibility=View.INVISIBLE
        super.onPause()
    }

    private fun onObserveQuerySearch() {

        Api_binding!!.search.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                //observing if user searches
                if(!newText.isNullOrEmpty()|| !newText.isNullOrBlank()){
                    tempstring=newText

                    viewModel.observeUsersLiveData(tempstring).observe(activity, Observer { it ->

                        if (it.isNullOrEmpty()){
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
    }

    private fun initialSetup() {
        activity = requireNotNull(getActivity())
        //making progress bar visible on roomdb accessing
        Api_binding!!.progressBarAP.visibility=View.VISIBLE

    //    prepareRecyclerView()
        Api_binding!!.Apirecyclerview.adapter=apiAdapter
        Api_binding!!.Apirecyclerview.layoutManager=LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this)[ApiViewModel::class.java]

    }


}