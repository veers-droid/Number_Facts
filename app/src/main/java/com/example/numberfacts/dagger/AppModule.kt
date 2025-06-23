package com.example.numberfacts.dagger

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.numberfacts.room.dao.StoryDao
import com.example.numberfacts.room.database.StoryDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideDatabase(app: Application): StoryDataBase {
        return Room.databaseBuilder(
            app,
            StoryDataBase::class.java,
            "story.db"
        )
            .createFromAsset("databases/story.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideStoryDao(db: StoryDataBase): StoryDao {
        return db.storyDao()
    }
}