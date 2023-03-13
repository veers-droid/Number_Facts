package com.example.numberfacts.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.numberfacts.retrofit.client.RetrofitClient
import com.example.numberfacts.room.entity.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SecondFragmentViewModel(application: Application) : MyFragmentViewModel(application) {
    val fact = MutableLiveData<String>()

    /**
     * this function gets fact about number from numbers api, written by user
     * after getting the fact, we are inserting it to the database
     */
    fun getFactAboutNumber() {
        RetrofitClient.client.getFactAboutNumber(number.value.toString()).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                fact.postValue(response.body())
                response.body()?.let { insert(it) }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }

        })

    }

    /**
     * this function gets fact about random number from numbers api
     * after getting the fact, we are inserting it to the database
     */
   fun getRandomFact() {
       RetrofitClient.client.getFactAboutRandomNumber().enqueue(object : Callback<String> {
           override fun onResponse(call: Call<String>, response: Response<String>) {
               number.postValue(response.body()?.split(" ")?.get(0)?.toIntOrNull())
               fact.postValue(response.body())
               response.body()?.let { insert(it) }
           }

           override fun onFailure(call: Call<String>, t: Throwable) {
               Log.d("TAG", t.message.toString())
           }
       })
   }

    /**
     * we need this function to insert the fact, we got, to our database
     */
   fun insert(fact: String) {
       viewModelScope.launch(Dispatchers.IO) {
           storyDao.insert(Story(text = fact))
       }
   }
}
