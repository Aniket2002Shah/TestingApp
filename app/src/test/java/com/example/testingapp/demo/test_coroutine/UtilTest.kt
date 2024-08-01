package com.example.testingapp.demo.test_coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*

class UtilTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var util:Util

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
       util = Util(mainCoroutineRule.testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUserName(){
        //runBlocking blocks --- slows testing
        //runTest = launch's scope ---> but dispatches over StandardTestDispatcher
        //get Queued all runTest()
        //use to call suspend/non-suspend fun()
        runTest {
            val str = util.getUserName()
            Assert.assertEquals("CheezyCode", str)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUser(){
        runTest {
            val str = util.getUser()      //Uses MAIN ---> which replaces by StandardTestDispatcher
            Assert.assertEquals("User - CheezyCode", str)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddress(){
        runTest {
            val str = util.getAddress()     //Uses constructor dispatcher --->IODispatcher(application)---> which replaces by StandardTestDispatcher(pass by constructor)
            Assert.assertEquals("Address", str)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddressDetail(){
        //Calling non-suspend fun --- OK
        runTest {
            util.getAdressDetail()     //Uses constructor dispatcher --->IODispatcher(application)---> which replaces by StandardTestDispatcher(pass by constructor)

            mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
            //StandardTestDispatcher ---By default not dispatches , thus work done but no chnages yet applied
            //Object not created ----> Dispatches defaulty
            //Object created ----> Force to Dispatch
            //to Dispatch use above


            Assert.assertEquals(true,util.globalArg)
        }
    }
}