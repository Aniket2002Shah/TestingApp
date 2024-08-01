package com.example.testingapp.demo

import org.junit.Test

import org.junit.Assert.*

/**
 * #Test in Android 2 types:--
 *
 * 1.Jvm Test/Local Test----->runs on JVM ----------> uses JUnit/Mockito ----> More test on Jvm is preferred
 *
 * 2.Android/Instrumentation Test ------>
 *       a.)Non-UI Test--> runs on JUnit-----> for application context,asset manager
 *                                      -----> can change them to Jvm Test using Mockito
 *
 *       b.)UI-Test -->Espresso---->for UI-interaction----->Activity/Fragment
 *
 *  ------------------------------------------------------------------------------------------
 *
 * #Test Run ----- 1.Run Classs 2.Run Function 3.Run With Coverage---> number of lines executed
 *
 *  --------------------------------------------------------------------------------------------------
 * #Function convention ---> funName_inputValue_expectedValue() -- ex: isPallindrome_inputHello_expectedTrue
 *     @Before== fun setUp(){}
 *     @After == teaDown()
 *     @ParameterizedClass ---- when same class/logic is used with diff value  --> ex: PallindromeParameterizedTest
 *
 *  * --------------------------------------------------------------------------------------------------
 *
 *  Repository,Dao ---- all which requires application context ---> Instrument Test
 *  But using Mocktio------> shift to ---> Jvm Test       //Jvm tests are preferred
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}