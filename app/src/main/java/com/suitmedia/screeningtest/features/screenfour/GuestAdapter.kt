package com.suitmedia.screeningtest.features.screenfour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.suitmedia.screeningtest.databinding.ItemGuestBinding
import com.suitmedia.screeningtest.features.screenone.ProfileEntity
import timber.log.Timber

class GuestAdapter: RecyclerView.Adapter<GuestAdapter.GuestViewHolder>() {
    private var list = ArrayList<ProfileEntity>()

    fun setList(data: List<ProfileEntity>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    private lateinit var guestCallback: GuestCallback
    fun setGuestCallback(guestCallback: GuestCallback) {
        this.guestCallback = guestCallback
    }

    inner class GuestViewHolder(private val binding: ItemGuestBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProfileEntity) {

            binding.lytParent.setOnClickListener {
                Timber.e(data.first_name)
                guestCallback.onItemClicked(data)
            }

            binding.apply {
                Glide.with(binding.root.context)
                    .load(data.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(image)
                name.text = data.first_name + " " + data.last_name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val items = ItemGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestViewHolder(items)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}