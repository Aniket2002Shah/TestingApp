package com.example.testingapp.project

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testingapp.database.Result
import com.example.testingapp.demo.test_coroutine.MainCoroutineRule
import com.example.testingapp.model.QuoteList
import com.example.testingapp.repository.QuoteRepository
import com.example.testingapp.response_handling.Response
import com.example.testingapp.util.getOrAwaitValue
import com.example.testingapp.view_model.MainViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()      //shifts all other scope to new scopes like IO

    @Mock
    lateinit var quoteRepository:QuoteRepository

    lateinit var viewModel : MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(quoteRepository,mainCoroutineRule.testDispatcher)
    }

    @After
    fun tearDown() {
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getQuotes_InputMock_expectQuotes(){
        runTest {
            val list = listOf<Result>(Result(0,"","Author","Hello","Hello"))
            val quoteMutableLiveData= MutableLiveData<Response<QuoteList>>()
            quoteMutableLiveData.postValue(Response.Success(QuoteList(1,0,0,list,1,1)))

            val quoteLiveData:LiveData<Response<QuoteList>> = quoteMutableLiveData

            Mockito.`when`(quoteRepository.quoteLiveData).thenReturn(quoteLiveData)


            assertTrue(viewModel.isInitBlockExecuted)        //testing init -----> TEST_DISPATCHER AUTOMATIC IN INIT()

            val data = viewModel.quoteLiveData.getOrAwaitValue().r_data?.results
            Assert.assertEquals(1,data!!.size)
            Assert.assertEquals("Author", data[0].author)


            //verify no returning fun()
//            verify(quoteRepository, times(1)).getQuotes(1)
//            verifyNoMoreInteractions(quoteRepository)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun  getQuotes_InputMock_expectError(){
        runTest{
            val quoteMutableLiveData= MutableLiveData<Response<QuoteList>>()
            quoteMutableLiveData.postValue(Response.Error("Error"))

            val quoteLiveData:LiveData<Response<QuoteList>> = quoteMutableLiveData

            Mockito.`when`(quoteRepository.quoteLiveData).thenReturn(quoteLiveData)



            val data = viewModel.quoteLiveData.getOrAwaitValue().r_message
            Assert.assertEquals("Error",data)
        }
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    //test dispatcher force -----> IN CASE OF FUNCTION CALLING
    fun testDispatcher(){
       runTest {
           viewModel.quotes()
           mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
           assertEquals(true,viewModel.isRunning)
       }
    }
}