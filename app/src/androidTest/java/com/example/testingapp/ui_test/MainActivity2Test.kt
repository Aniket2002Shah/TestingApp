package com.example.testingapp.ui_test;

import android.app.Instrumentation
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testingapp.MainActivity
import com.example.testingapp.RecylerViewAdapter
import org.hamcrest.Matchers.allOf
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class MainActivity2Test {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()


    @Test
    //Recylerview
    fun A_testQuote_OnRecyclerClick(){
        Thread.sleep(1000)
        onView(withId(com.example.testingapp.R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToLastPosition<RecylerViewAdapter.RecyclerViewHolder>())
            .perform(click()) //swipes till end
        onView(withId(com.example.testingapp.R.id.author)).check(matches(withText("Thomas Edison")))
    }

    @Test
    //Intent(Implicit)
    fun B_testQuote_shareIntent(){
        Thread.sleep(1000)
        onView(withId(com.example.testingapp.R.id.recyclerView)).perform(click())

        Intents.init()
        val intent = allOf(hasAction(Intent.ACTION_SEND), hasType("text/plain"))
        val expectedIntent = allOf(hasAction(Intent.ACTION_CHOOSER),
             hasExtra(Intent.EXTRA_INTENT,intent),
            hasExtra(Intent.EXTRA_TITLE,"SHARE this quote....."))


        onView(withId(com.example.testingapp.R.id.share)).perform(click())

        intended(expectedIntent)
        Intents.release()
    }
}