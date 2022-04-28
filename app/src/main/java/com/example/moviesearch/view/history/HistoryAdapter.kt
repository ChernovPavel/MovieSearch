package com.example.moviesearch.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.model.Movie

class HistoryAdapter :
    RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {
    private var data: List<Movie> = arrayListOf()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_history_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val recyclerViewItem = view.findViewById<TextView>(R.id.recyclerViewItem)

        fun bind(data: Movie) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                recyclerViewItem.text =
                    String.format(
                        "%s", data.title
                    )
            }
        }
    }
}