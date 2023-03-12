package com.example.numberfacts.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.numberfacts.room.dao.StoryDao
import com.example.numberfacts.room.entity.Story

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class StoryDataBase: RoomDatabase(){

    abstract fun storyDao(): StoryDao

    companion object {
        private const val DATABASE_NAME = "story.db"

        @Volatile
        private var instance: StoryDataBase? = null

        fun getInstance(context: Context): StoryDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDataBase::class.java,
                    DATABASE_NAME
                )
                .createFromAsset("databases/$DATABASE_NAME")
                .fallbackToDestructiveMigration()
                .build().also { instance = it }
            }
        }
    }
}
