package com.example.task2kotlin
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
    fun getUserData() : Boolean{
        var output : Boolean=true
        RetrofitInstance.usersinterface.GetuserData().enqueue(object : Callback<usersData> {

            override fun onResponse(call: Call<usersData>, response: Response<usersData>) {
                if (response.body() != null) {
                    usersLiveData.value = response.body()!!.data

                } else {
                    output=false
                    return
                }
            }

            override fun onFailure(call: Call<usersData>, t: Throwable) {
                output=false

            }
        })



    return output
    }

    //observeUserLiveData
    fun observeUsersLiveData(search: String): LiveData<List<Data>> {

        //if search is null then return whole dataset
        if (search.equals("default")) {

            return usersLiveData

        }

        //if user searching then filter data and return

       if(!usersLiveData.value.isNullOrEmpty()){
        temp.value = usersLiveData.value
        temp= temp.map { it?.filter { it.first_name.contains(search, ignoreCase = true) } } as MutableLiveData<List<Data>>
       }
        return temp

    }
}
