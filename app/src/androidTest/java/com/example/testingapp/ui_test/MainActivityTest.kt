package com.example.testingapp.ui_test;

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
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

/**
 * It act as    IntegrationTest --> between each Activity/Fragment + MVVM
 * Since Each Activity/Fragment interacts-----> E2E Test
 * Since test DB offline ---> SQLiteDB Test
 * Since test server online ---> Server Test
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun A_testViewPage_TabLayout(){
        onView(withId(com.example.testingapp.R.id.viewPage)).perform(swipeLeft()).perform(click())
        onView(withId(com.example.testingapp.R.id.add_note)).check(matches(withText("Add Note")))

        onView(withId(com.example.testingapp.R.id.viewPage)).perform(swipeRight()).perform(click())
        onView(withId(com.example.testingapp.R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToLastPosition<RecylerViewAdapter.RecyclerViewHolder>())

        onView(withId(com.example.testingapp.R.id.tablayout)).check(matches(isDisplayed()))
        //Testing each component differs like TabLayout diff from Navigation
        //Just search on ChatGPT and Do

    }
}