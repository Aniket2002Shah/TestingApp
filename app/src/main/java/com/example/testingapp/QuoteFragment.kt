package com.example.testingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingapp.database.Result
import com.example.testingapp.databinding.FragmentQuoteBinding
import com.example.testingapp.response_handling.Response
import com.example.testingapp.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteFragment : Fragment(),RecylerViewAdapter.OnItemClick {

    private lateinit var binding:FragmentQuoteBinding
    private lateinit var adapter: RecylerViewAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil
            .inflate<FragmentQuoteBinding>(inflater, R.layout.fragment_quote, container, false)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = RecylerViewAdapter(this)
        binding.recyclerView.adapter = adapter

        mainViewModel.quoteLiveData.observe(viewLifecycleOwner) {
            when(it) {

                is Response.Processing->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Response.Success-> {
                    it?.let {
                        binding.progressBar.visibility = View.GONE
                        val quote = it.r_data?.results
                        adapter.submitList(quote)
                    }
                }
                is Response.Error->{
                    it?.let {
                        Toast.makeText(activity,it.r_message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return binding.root
    }

    override fun onClick(item: Result) {
        val intent = Intent(activity, MainActivity2::class.java)
        intent.putExtra("author",item.author)
        intent.putExtra("quote",item.content)
        startActivity(intent)
    }

}