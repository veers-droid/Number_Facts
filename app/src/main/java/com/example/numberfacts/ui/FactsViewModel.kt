package com.example.numberfacts.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberfacts.retrofit.repository.NumberFactsRepository
import com.example.numberfacts.room.dao.StoryDao
import com.example.numberfacts.room.entity.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactsViewModel @Inject constructor(
    private val repository : NumberFactsRepository,
    private val dao : StoryDao
) : ViewModel() {

    private val _fact : MutableLiveData<String> = MutableLiveData()
    val fact : LiveData<String>
        get() = _fact


    private val _story = mutableStateOf<List<String>>(emptyList())
    val story: State<List<String>> = _story

    private var alreadyRequested = false

    /**
     * this function gets fact about number from numbers api, written by user
     * after getting the fact, we are inserting it to the database
     */
    fun getFactAboutNumber(num : String) {
        if (alreadyRequested) return
        alreadyRequested = true
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
        if (alreadyRequested) return
        alreadyRequested = true
        viewModelScope.launch {
            _fact.value = repository.getFactAboutRandomNumber()
            insertToDB()
        }
    }

    /**
     * This function inserts to database our fact after we get it
     */
    private suspend fun insertToDB() {
        dao.insert(Story(text = _fact.value!!))
    }

    /**
     * this function gets 20 rows with fact from db
     * and puts the value to our LiveData
     */
    fun getStory() {
          viewModelScope.launch (Dispatchers.IO) {
            val storyList = ArrayList(dao.getAll())
            val factList = ArrayList<String>()
            if (storyList.isNotEmpty()) {
                for (fact in storyList) {
                    factList.add(fact.text)
                }
            }
            _story.value = factList
        }
    }
}