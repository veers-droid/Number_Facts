package com.example.numberfacts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberfacts.retrofit.repository.NumberFactsRepository
import com.example.numberfacts.room.dao.StoryDao
import com.example.numberfacts.room.entity.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactsViewModel @Inject constructor(
    private val repository : NumberFactsRepository,
    private val dao : StoryDao
) : ViewModel() {

    private val _fact : MutableLiveData<String> = MutableLiveData()
    val fact : LiveData<String>
        get() = _fact


    /**
     * this function gets fact about number from numbers api, written by user
     * after getting the fact, we are inserting it to the database
     */
    fun getFactAboutNumber(num : String) {
        viewModelScope.launch {
            _fact.value = repository.getFactAboutNumber(num)
            insertToDB()
        }
    }

    /**
     * this function gets fact about random number from numbers api
     * after getting the fact, we are inserting it to the database
     */
    fun getRandomFact() {
        viewModelScope.launch {
            _fact.value = repository.getFactAboutRandomNumber()
            insertToDB()
        }
    }

    /**
     * This function inserts to database our fact after we get it
     */
    private fun insertToDB() {
        dao.insert(Story(text = _fact.value!!))
    }

    /**
     * this function gets 20 rows with fact from db
     * and puts the value to our LiveData
     */
    suspend fun getStory() : List<String> {
          val res = viewModelScope.async(Dispatchers.IO) {
            val storyList = ArrayList(dao.getAll())
            val factList = ArrayList<String>()
            if (storyList.isNotEmpty()) {
                for (fact in storyList) {
                    factList.add(fact.text)
                }
            }
            factList
        }
        return res.await()
    }
}