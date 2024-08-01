package com.example.testingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.testingapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var quote: String
    private lateinit var author:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        author = intent.extras!!.getString("author")!!
        quote = intent.extras!!.getString("quote")!!

        binding.author.text = author
        binding.quote.text = quote

        binding.share.setOnClickListener {
            share(quote)
        }
    }

    fun share( quote:String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,quote)
        val chooser = Intent.createChooser(intent, "SHARE this quote.....")
        startActivity(chooser)
    }
}