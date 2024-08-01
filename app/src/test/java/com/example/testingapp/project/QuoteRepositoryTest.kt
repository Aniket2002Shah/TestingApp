package com.example.testingapp.project

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testingapp.database.Result
import com.example.testingapp.database.ResultDao
import com.example.testingapp.demo.test_coroutine.MainCoroutineRule
import com.example.testingapp.dependency_injection.RoomDatabase
import com.example.testingapp.model.QuoteList
import com.example.testingapp.repository.QuoteRepository
import com.example.testingapp.response_handling.Response
import com.example.testingapp.util.NetworkConnection
import com.example.testingapp.util.Quote
import com.example.testingapp.util.getOrAwaitValue
import com.example.testingapp.view_model.MainViewModel
import com.example.testingapp.web_services.QuotesServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class QuoteRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()      //shifts all other scope to new scopes like IO

    lateinit var quoteRepository: QuoteRepository

    @Mock
    lateinit var quotesServices: QuotesServices

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var networkConnection: NetworkConnection

    @Mock
    lateinit var resultDao: ResultDao

    @Mock
    lateinit var response: retrofit2.Response<QuoteList>

    @Mock
    lateinit var roomDatabase: RoomDatabase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
       quoteRepository = QuoteRepository(quotesServices,roomDatabase,context,networkConnection)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getQuoteListroomDb_Offline() =runTest {
            val list = listOf<Result>(Result(0,"","Author","Hello","Hello"))
            Mockito.`when`(networkConnection.isInternetAvailable(context)).thenReturn(false)
            Mockito.doReturn(resultDao).`when`(roomDatabase).resultDao()
            Mockito.`when`(roomDatabase.resultDao().getQuotes()).thenReturn(list)
            quoteRepository.getQuotes(1)
            val data =quoteRepository.quoteLiveData.getOrAwaitValue().r_data?.results
            Assert.assertEquals(1,data!!.size)
            Assert.assertEquals("Author", data[0].author)
        }



    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getQuoteListWebServcie_Online_Error() =runTest {
        val list = emptyList<com.example.testingapp.database.Result>()
        val ql = QuoteList(1,0,1,list,1,1)
        Mockito.`when`(networkConnection.isInternetAvailable(context)).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(null)
        Mockito.`when`(response.code()).thenReturn(401)
        Mockito.`when`(quotesServices.getQuotes(1)).thenReturn(response)


        quoteRepository.getQuotes(1)
        val data =quoteRepository.quoteLiveData.getOrAwaitValue().r_message
        Assert.assertEquals("API not respond",data)
    }


    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getQuoteListWebServcie_Online_Result() =runTest {
        val list = listOf<Result>(Result(0,"","Author","Hello","Hello"))
        val ql = QuoteList(1,0,1,list,1,1)
        Mockito.`when`(networkConnection.isInternetAvailable(context)).thenReturn(true)
        Mockito.doReturn(resultDao).`when`(roomDatabase).resultDao()
        Mockito.`when`(roomDatabase.resultDao().getQuotes()).thenReturn(list)
        Mockito.`when`(response.body()).thenReturn(ql)
        Mockito.`when`(quotesServices.getQuotes(1)).thenReturn(response)


        quoteRepository.getQuotes(1)
        val data = quoteRepository.quoteLiveData.getOrAwaitValue().r_data?.results
        Assert.assertEquals(1,data!!.size)
        Assert.assertEquals("Author", data[0].author)
    }


}
