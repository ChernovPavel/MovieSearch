package com.example.moviesearch.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.model.Movie

class ListFragmentAdapter(private var onItemViewClickListener: ListFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<ListFragmentAdapter.MainViewHolder>() {

    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            itemView.apply {
                findViewById<TextView>(R.id.mainFragmentRecyclerItemMovieName).text = movie.name
                findViewById<TextView>(R.id.mainFragmentRecyclerItemMovieGenre).text = movie.genre
                findViewById<TextView>(R.id.mainFragmentRecyclerItemMovieReleaseDate).text =
                    movie.release_date
                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(movie)
                }
            }
        }
    }
}