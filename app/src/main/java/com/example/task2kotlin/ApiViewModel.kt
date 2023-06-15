package com.example.task2kotlin
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.task2kotlin.Retrofit.Data
import com.example.task2kotlin.Retrofit.RetrofitInstance
import com.example.task2kotlin.Retrofit.usersData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiViewModel : ViewModel() {
    private var usersLiveData = MutableLiveData<List<Data>>()
    private var temp = MutableLiveData<List<Data>>()

    //GetUserData
    fun GetuserData(progressBarAP: ProgressBar) {
        RetrofitInstance.usersinterface.GetuserData().enqueue(object : Callback<usersData> {

            override fun onResponse(call: Call<usersData>, response: Response<usersData>) {
                if (response.body() != null) {
                    usersLiveData.value = response.body()!!.data
                    progressBarAP.visibility= View.INVISIBLE
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<usersData>, t: Throwable) {
            }
        })
    }

    //observeUserLiveData
    fun observeUsersLiveData(search: String): LiveData<List<Data>> {

        //if search is null then return whole dataset
        if (search.equals("default")) {

            return usersLiveData

        }

        //if user searching then filter data and return
        temp.value = usersLiveData.value
        temp= temp.map { it.filter { it.first_name.contains(search, ignoreCase = true) } } as MutableLiveData<List<Data>>
        return temp

    }
}
