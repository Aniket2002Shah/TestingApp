package com.example.testingapp.demo.test_coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/** We create a rule to:-------- Single place
 *  1.Set Main Coroutine
 *  2.Create a dispatchers object
 *
 *  Rather writing same code in each test
 *  This is Watcher class runs before test ---- config file
 */
class MainCoroutineRule : TestWatcher() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()
    //testing has one coroutine = StandardTestDispatcher
    //other coroutine dispatch over it
    // advice to run all test on single coroutine


    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)       //set Main.Coroutine = StandardTestDispatcher   ---> testing has no MAIN.Scope
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()                 //reset
    }
}