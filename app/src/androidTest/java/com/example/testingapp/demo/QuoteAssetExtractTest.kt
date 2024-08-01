package com.example.testingapp.demo

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testingapp.util.Quote
import com.example.testingapp.util.QuoteAssetExtract
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.internal.MethodSorter
import org.junit.runner.OrderWith
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.io.FileNotFoundException

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
 class QuoteAssetExtractTest {

    @Before
    //@BeforeClass
    fun setUp() {
    }

    @After
    //@AfterClass
    fun tearDown() {
    }

    //Test to check throw exception if filename is wrong
    //Similarly we can test for diff exception like:
    //JsonSyntaxException,RuntimeException,I/OException
    @Test(expected = FileNotFoundException::class)
    fun A_test_populateQuoteFromAssets_InputInvalid_ExpectedException(){
    val quoteExrt = QuoteAssetExtract()
        val context = ApplicationProvider.getApplicationContext<Context>()
        quoteExrt.populateQuoteFromAssets(context,"")
    }

    //Validity
    @Test
    fun B_test_populateQuoteFromAssets_ValidJson_Count() {
        val quoteExrt = QuoteAssetExtract()
        val context = ApplicationProvider.getApplicationContext<Context>()
        quoteExrt.populateQuoteFromAssets(context,"quote.json")
        assertEquals(266,quoteExrt.quoteList.size)
    }

    @Test
    fun C_test_NextQuote_InputList_Expect_List() {
        val quoteExrt = QuoteAssetExtract()
        quoteExrt.populateQuotes(
            arrayOf(
                 Quote("This is first","1"),
                 Quote("This is second","2") ,
                 Quote("This is third","3")
            )
        )
        val quote = quoteExrt.getNextQuote()
        assertEquals("2",quote.author)
    }

    @Test
    fun D_test_PrevQuote_InputList_Expect_List() {
        val quoteExrt = QuoteAssetExtract()
        quoteExrt.populateQuotes(
            arrayOf(
                Quote("This is first","1"),
                Quote("This is second","2") ,
                Quote("This is third","3")
            )
        )
        val quote = quoteExrt.getPreviousQuote()
        assertEquals("1",quote.author)
    }
}