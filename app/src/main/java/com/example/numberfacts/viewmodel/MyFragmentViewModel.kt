package com.example.numberfacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numberfacts.room.dao.StoryDao
import com.example.numberfacts.room.database.StoryDataBase

open class MyFragmentViewModel(application: Application): ViewModel() {
    val number = MutableLiveData<Int>(null)

    val storyDao: StoryDao

    init {
        val dataBase = StoryDataBase.getInstance(application)
        storyDao = dataBase.storyDao()
    }
}
