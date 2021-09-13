package com.engelsizyasam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.R
import com.engelsizyasam.databinding.CardItemSeriesDetailBinding
import com.engelsizyasam.network.SeriesModel
import com.squareup.picasso.Picasso

class SeriesDetailAdapter(private val clickListener: SeriesDetailListener) : RecyclerView.Adapter<SeriesDetailAdapter.ViewHolder>() {

    var data = listOf<SeriesModel.İtem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: CardItemSeriesDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeriesModel.İtem, clickListener: SeriesDetailListener) {
            binding.seriesModel = item
            binding.title.text = item.snippet.title
            try {
                Picasso.get().load(item.snippet.thumbnails.medium.url).into(binding.image)
            } catch (e: Exception) {
                Picasso.get().load(R.drawable.null_video).into(binding.image)

            }
            binding.clickListener = clickListener

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardItemSeriesDetailBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

}

class SeriesDetailListener(val clickListener: (link: String) -> Unit) {
    fun onClick(seriesModel: SeriesModel.İtem) = clickListener(seriesModel.snippet.resourceId.videoId)
}