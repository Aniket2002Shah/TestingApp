package com.example.testingapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testingapp.model.QuoteList
import com.example.testingapp.response_handling.Response
import com.example.testingapp.util.NetworkConnection
import com.example.testingapp.dependency_injection.RoomDatabase
import com.example.testingapp.web_services.QuotesServices
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteService: QuotesServices, private val roomDatabase: RoomDatabase, private val application: Context,private val networkConnection: NetworkConnection) {
    private var quoteMutableLiveData= MutableLiveData<Response<QuoteList>>()

    val quoteLiveData:LiveData<Response<QuoteList>>
    get()= quoteMutableLiveData

    suspend fun getQuotes(page:Int){

        if(networkConnection.isInternetAvailable(application)) {
            try {
                val result = quoteService.getQuotes(page)
                quoteMutableLiveData.postValue(Response.Processing())
                if (result.body() != null) {
                    roomDatabase.resultDao().deleteQuotes()
                    roomDatabase.resultDao().addQuotes(result.body()!!.results)
                    quoteMutableLiveData.postValue(Response.Success(result.body()))
                }
                else{
                    quoteMutableLiveData.postValue(Response.Error("API not respond"))
                }
            }
            catch (e:Exception){
                quoteMutableLiveData.postValue(Response.Error(e.message))
            }
        }
        else{
            try {
            val quote= roomDatabase.resultDao().getQuotes()
            val quotelist = QuoteList(0,0,1,quote,0,0)
            quoteMutableLiveData.postValue(Response.Success(quotelist))

        }
            catch (e:Exception){
                quoteMutableLiveData.postValue(Response.Error(e.message))
            }
        }
    }

    suspend fun getRandomQuotes(){
       val value= (Math.random()*10).toInt()
        getQuotes(value)

    }
}