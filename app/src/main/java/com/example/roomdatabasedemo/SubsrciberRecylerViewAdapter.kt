package com.example.roomdatabasedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.roomdatabasedemo.databinding.ListItemBinding

class SubsrciberRecylerViewAdapter(private val subscribers:List<Subscriber>, private val clicklistener:(Subscriber) -> Unit): RecyclerView.Adapter<subscriberViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): subscriberViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return subscriberViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    override fun onBindViewHolder(holder: subscriberViewHolder, position: Int) {
       val subscriber = subscribers[position]
        holder.bind(subscriber, clicklistener)
    }
}

class subscriberViewHolder(val binding: ListItemBinding): ViewHolder(binding.root){
    fun bind(subscriber: Subscriber, clicklistener:(Subscriber) -> Unit){
        binding.inputName.text = subscriber.name
        binding.inputEmail.text = subscriber.email
        binding.listItemLayout.setOnClickListener { clicklistener(subscriber) }

    }
}