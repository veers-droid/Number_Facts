package com.example.numberfacts.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.numberfacts.room.entity.Story

@Dao
interface StoryDao {

    @Query("SELECT * FROM stories ORDER BY id DESC LIMIT 20")
    suspend fun getAll() : List<Story>

    @Insert
    suspend fun insert(story: Story)

}
