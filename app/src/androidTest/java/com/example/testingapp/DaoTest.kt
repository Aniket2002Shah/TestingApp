package com.example.testingapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.testingapp.database.Result
import com.example.testingapp.database.ResultDao
import com.example.testingapp.dependency_injection.RoomDatabase
import com.example.testingapp.util.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() //executes Architecture Components synchronously

    lateinit var  quoteDatabase: RoomDatabase
    lateinit var resultDao: ResultDao

    @Before
    fun setUp(){
        //creating a fake in-memory DB for test----> H2B like
        quoteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),RoomDatabase::class.java)
            .allowMainThreadQueries().build()
        resultDao = quoteDatabase.resultDao()
    }

    @Test
    fun insertQuote_expectedResultList() = runBlocking{
        val resultList = listOf<com.example.testingapp.database.Result>(Result(0,"0","Aniket","","There is no more salvation than never being born in this world"))
        resultDao.addQuotes(resultList)

        val result = resultDao.getQuotesList().getOrAwaitValue()
        Assert.assertEquals(1,result.size)
        Assert.assertEquals("Aniket",result[0].author)
    }
}