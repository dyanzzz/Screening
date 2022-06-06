package com.suitmedia.screeningtest.features.screenthree

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suitmedia.screeningtest.R
import com.suitmedia.screeningtest.databinding.ItemEventBinding

class EventAdapter: RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    private var list = ArrayList<EventEntity>()

    fun setList(data: ArrayList<EventEntity>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var eventCallback: EventCallback
    fun setEventCallback(eventCallback: EventCallback) {
        this.eventCallback = eventCallback
    }

    inner class EventViewHolder(private val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EventEntity) {

            binding.root.setOnClickListener {
                eventCallback.onItemClicked(data)
            }

            binding.apply {
                titleEvent.text = data.title
                bodyEvent.text = data.body
                date.text = data.date
                time.text = data.time

                imageEvent.setImageResource(R.drawable.image_event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val items = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(items)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}