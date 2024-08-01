package com.example.testingapp.demo

import android.content.Context
import android.content.res.AssetManager
import com.example.testingapp.util.QuoteAssetExtract
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

/**QuoteExtract test uses [aaplicationContext] and [asset]  ----> Run = Instrumentation Test
 * Problems:   slow,costly
 *
 * To avoid Run [JVM Test] -----> Mockito
 * **/
class QuoteExtractAssetJvmTest {

    @Mock
    lateinit var context:Context

    @Mock
    lateinit var assetManager: AssetManager

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test(){
        val testStream = QuoteExtractAssetJvmTest::class.java.getResourceAsStream("/quote.json")
        doReturn(assetManager).`when`(context).assets
        Mockito.`when`(context.assets.open(anyString())).thenReturn(testStream)

        val quote = QuoteAssetExtract()
        quote.populateQuoteFromAssets(context,"")
        val current = quote.getCurrentQuote()

        Assert.assertEquals(2,quote.quoteList.size)
        Assert.assertEquals( "Genius is one percent inspiration and ninety-nine percent perspiration.",current.text)
        Assert.assertEquals("Thomas Edison",current.author)
    }
}