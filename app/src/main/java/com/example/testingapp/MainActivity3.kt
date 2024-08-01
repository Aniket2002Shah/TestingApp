package com.example.testingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.testingapp.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding

    private lateinit var title: String
    private lateinit var desc:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3)

        title = intent.extras!!.getString("title")!!
        desc = intent.extras!!.getString("description")!!

        binding.titQuote.text = title
        binding.descAuthor.text = desc

    }
}