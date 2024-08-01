package com.example.testingapp.demo.test_coroutine

import kotlinx.coroutines.*

class Util(val dispatcher: CoroutineDispatcher) {

    suspend fun getUserName():String{
        delay(10000)
        return "CheezyCode"
    }

    suspend fun getUser():String{
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
        }
        return "User - CheezyCode"
    }


    var globalArg = false
    fun getAdressDetail(){
        CoroutineScope(dispatcher).launch {
            globalArg = true
        }
    }


    /** We can't reset other coroutines except [Main]
     * So pass it in constructor so that it can be---->  used in Application & Testing----> through constructor
     *
     * ex: class Util(dispatchers:Dispatchers){}
     *
     * ex: Appliaction --                                         ex:Testing
     * val util = Util(Dispatchers.IO)                            val uitlTest = Util(dispatcher)
     * with(dispatchers){}                                        withyDispatchers(dispatchers){}
     * **/
    suspend fun getAddress():String{
        withContext(dispatcher){
            delay(1000)
        }
        return "Address"
    }
}