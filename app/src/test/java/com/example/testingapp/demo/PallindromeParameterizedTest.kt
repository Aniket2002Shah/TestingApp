package com.example.testingapp.demo

import com.example.testingapp.util.Pallindrome
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * when same function is used for checking diff input values we use Parameterized
 * **/

@RunWith(value = Parameterized::class)
class PallindromeParameterizedTest(private val input :String, private val expectedValue:Boolean) {

    @Test
    fun test(){
        val helper = Pallindrome()
        val result = helper.isPallindrome(input)
        assertEquals(expectedValue,result)
    }

    companion object{

        @JvmStatic
        @Parameterized.Parameters(name = "{index} : {0} is Palindrome--> {1}")
        fun data():List<Array<Any>>{
            return listOf(
                arrayOf("hello",false),
                arrayOf("level",true),
                arrayOf("a",true),
                arrayOf(" ",true)
            )
        }
    }
}