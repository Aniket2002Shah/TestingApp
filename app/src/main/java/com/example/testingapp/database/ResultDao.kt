package com.example.testingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResultDao {
    @Insert
    suspend fun addQuotes(result: List<Result>)

    @Query("delete from quote_table")
    suspend fun deleteQuotes()

    @Query("select * from quote_table")
    suspend fun getQuotes():List<Result>

    @Query("select * from quote_table")
    fun getQuotesList():LiveData<List<Result>>
}