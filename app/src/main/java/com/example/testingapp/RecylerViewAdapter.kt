package com.example.testingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testingapp.database.Result
import com.example.testingapp.databinding.ItemActivityBinding

class RecylerViewAdapter(private val item: RecylerViewAdapter.OnItemClick):ListAdapter<Result,RecylerViewAdapter.RecyclerViewHolder>(DiffUtil()) {

    class DiffUtil:androidx.recyclerview.widget.DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
           return oldItem._id==newItem._id
        }

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem==newItem
        }

    }
    class RecyclerViewHolder(private val binding: ItemActivityBinding, itemView: View): RecyclerView.ViewHolder(itemView) {
        fun insert(item:Result){
            binding.name.text=item.content
            binding.description.text= item.author
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<ItemActivityBinding>(inflater,R.layout.item_activity,parent,false)

        val viewHolder =  RecyclerViewHolder(binding, binding.root)
        binding.itemQuote.setOnClickListener {
            item.onClick(currentList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
       val item = getItem(position)
        holder.insert(item)
    }

    interface OnItemClick{
        fun onClick(item:Result)
    }
}