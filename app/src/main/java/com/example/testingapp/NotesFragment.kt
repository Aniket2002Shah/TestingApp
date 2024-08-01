package com.example.testingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.testingapp.databinding.FragmentNotesBinding
import com.example.testingapp.databinding.FragmentQuoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNotesBinding>(inflater, R.layout.fragment_notes, container, false)


        binding.submit.setOnClickListener {
            val title= binding.tit.text.toString()
            val description = binding.desc.text.toString()

              submit(title, description)
        }

        return binding.root
    }

    fun submit(title:String,description:String){
        val intent = Intent(activity,MainActivity3::class.java)
        intent.putExtra("title",title)
        intent.putExtra("description",description)
        startActivity(intent)
    }
}