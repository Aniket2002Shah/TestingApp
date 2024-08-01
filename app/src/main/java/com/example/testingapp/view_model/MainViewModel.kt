package com.example.testingapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testingapp.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//use HiltViewModel when all params are injected or autowired self
//if not make Factory as pass via Activity/Fragment
class MainViewModel  @Inject constructor(private val quoteRepository: QuoteRepository, private val dispatcher: CoroutineDispatcher):ViewModel() {
    var isInitBlockExecuted = false       //testing
    var isRunning = false                  //testing

     init {
     viewModelScope.launch(Dispatchers.IO){
         quoteRepository.getQuotes(1)
         isInitBlockExecuted  = true  //testing
       }
     }
    val quoteLiveData
    get() = quoteRepository.quoteLiveData



    //to test dispatcher ----> fun for testing
    fun quotes(){
        viewModelScope.launch(dispatcher){
            quoteRepository.getQuotes(1)
            isRunning = true
        }
    }

}