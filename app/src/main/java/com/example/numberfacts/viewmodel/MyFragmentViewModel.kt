package com.example.numberfacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numberfacts.room.dao.StoryDao
import com.example.numberfacts.room.database.StoryDataBase

/**open class which needs for creation instance of database, also our model have same field number
 * for creating instance of view model with possibility of creation of database instance
 * we have to put the Application, so we have to create our viewModel via ViewModelProvider.Factory()
 */
open class MyFragmentViewModel(application: Application): ViewModel() {
    val number = MutableLiveData<Int>(null)

    val storyDao: StoryDao

    init {
        val dataBase = StoryDataBase.getInstance(application)
        storyDao = dataBase.storyDao()
    }
}
