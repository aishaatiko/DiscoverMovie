package com.alicea.discovermovie.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: List<SavedMovie>)
    @Update
    fun update(movie: List<SavedMovie>)
    @Delete
    fun delete(movie: List<SavedMovie>)
    @Query("SELECT * from savedmovie ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<SavedMovie>>
}