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
class QuoteFragment {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    //Recylerview
    fun A_testRecyclerView_Last(){
        Thread.sleep(1000)
        onView(withId(com.example.testingapp.R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToLastPosition<RecylerViewAdapter.RecyclerViewHolder>().apply {
                this.constraints.matches(com.example.testingapp.R.id.description)
                this.constraints.matches(com.example.testingapp.R.id.name)
                this.constraints.matches(withText("Thomas Edison"))
            }
        ) //swipes till end
    }

    @Test
    fun B_testRecyclerView_Fourth(){
        Thread.sleep(1000)
        onView(withId(com.example.testingapp.R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToPosition<RecylerViewAdapter.RecyclerViewHolder>(4).apply {
                this.constraints.matches(withText("Charles Dickens"))
            }
        )
    }

}