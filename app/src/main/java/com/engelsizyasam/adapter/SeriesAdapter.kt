package com.engelsizyasam.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.engelsizyasam.databinding.CardItemSeriesBinding
import com.engelsizyasam.model.SeriesModel
import com.squareup.picasso.Picasso

class SeriesAdapter(private val application: Application, private val clickListener: SeriesListener) : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    var data = listOf<SeriesModel.İtem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var seriesFilterList = ArrayList<SeriesModel.İtem>()

    init {
        seriesFilterList.addAll(data)
    }

    var count = 0

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                count = 0
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    seriesFilterList = data as ArrayList<SeriesModel.İtem>
                } else {
                    val resultList = ArrayList<SeriesModel.İtem>()
                    for (row in data) {
                        if (row.snippet.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    seriesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = seriesFilterList
                count = 1
                return filterResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results!!.count > 0) {
                    seriesFilterList = results.values as ArrayList<SeriesModel.İtem>
                }
                notifyDataSetChanged()

            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = when (count) {
            0 -> data[position]
            else -> seriesFilterList[position]
        }
        holder.bind(item, application, clickListener)

    }

    class ViewHolder private constructor(val binding: CardItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeriesModel.İtem, application: Application, clickListener: SeriesListener) {
            binding.seriesModel = item
            Picasso.get().load(item.snippet.thumbnails.medium.url).into(binding.image)
            binding.clickListener = clickListener

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardItemSeriesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = when (count) {
        0 -> data.size
        else -> seriesFilterList.size
    }

}

class SeriesListener(val clickListener: (link: String, link2: String) -> Unit) {
    fun onClick(seriesModel: SeriesModel.İtem) = clickListener(seriesModel.id, seriesModel.snippet.title)
}