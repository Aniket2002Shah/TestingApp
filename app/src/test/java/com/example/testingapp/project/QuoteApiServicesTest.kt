package com.example.testingapp.project

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testingapp.database.Result
import com.example.testingapp.demo.test_coroutine.MainCoroutineRule
import com.example.testingapp.model.QuoteList
import com.example.testingapp.repository.QuoteRepository
import com.example.testingapp.web_services.QuotesServices
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuoteApiServicesTest {

    lateinit var mockWebServer: MockWebServer

    lateinit var quotesServices: QuotesServices


    @Before
    fun setUp() {
       mockWebServer = MockWebServer()
        quotesServices = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(QuotesServices::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getQuote_expecetdNull()= runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = quotesServices.getQuotes(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(0, response.body()?.count)
        Assert.assertEquals(null, response.body()?.results)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getQuote_expecetdList()= runTest {
        val list = listOf<Result>(Result(0,"","Author","Hello","Hello"))
        val ql = QuoteList(1,0,1,list,1,1)
        val input = Gson().toJson(ql)
        val mockResponse = MockResponse()
        mockResponse.setBody(input)                                                        //mock input --> JSON
        mockWebServer.enqueue(mockResponse)

        val response = quotesServices.getQuotes(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(1, response.body()?.count)
        Assert.assertEquals(1, response.body()?.results?.size )
        Assert.assertEquals("Author", response.body()?.results?.get(0)!!.author)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getQuote_expectedError()= runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(501)
        mockResponse.setBody("Server Error")                                                        //mock input --> JSON
        mockWebServer.enqueue(mockResponse)

        val response = quotesServices.getQuotes(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(501, response.code() )
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

}