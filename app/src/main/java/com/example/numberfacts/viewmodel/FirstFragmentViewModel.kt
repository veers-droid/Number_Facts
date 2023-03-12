package com.example.numberfacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragmentViewModel(application: Application) : MyFragmentViewModel(application) {
    val facts = MutableLiveData<ArrayList<String>>()

    fun getStory() {
        viewModelScope.launch(Dispatchers.IO) {
            val storyList = ArrayList(storyDao.getAll())
            val factList = ArrayList<String>()
            if (storyList.isNotEmpty()) {
                for (fact in storyList) {
                    factList.add(fact.text)
                }
            }
            facts.postValue(factList)
        }
    }
}
