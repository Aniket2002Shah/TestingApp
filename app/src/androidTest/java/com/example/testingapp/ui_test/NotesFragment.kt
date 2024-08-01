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
class NotesFragment {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun A_testNotesFragemt_typeTest(){
        onView(withId(com.example.testingapp.R.id.viewPage)).perform(swipeLeft()).perform(click())

        onView(withId(com.example.testingapp.R.id.add_note)).check(matches(withText("Add Note")))

        onView(withId(com.example.testingapp.R.id.tit)).perform(typeText("Hello"))
        onView(withId(com.example.testingapp.R.id.desc)).perform(typeText("Aniket"),closeSoftKeyboard())
    }
}