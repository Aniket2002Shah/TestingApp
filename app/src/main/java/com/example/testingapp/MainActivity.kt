package com.example.testingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.testingapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private  val tabllayoutList = arrayOf("Quote","Notes")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(QuoteFragment())
        adapter.addFragment(NotesFragment())

        binding.viewPage.let {it->
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.adapter = adapter
        }

        TabLayoutMediator(binding.tablayout,binding.viewPage){tab,position->
            tab.text = tabllayoutList[position]
        }.attach()

    }
}