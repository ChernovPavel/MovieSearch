package com.example.moviesearch.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.model.Movie
import com.example.moviesearch.utils.RectangleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recycler_item.view.*

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
                mainFragmentRecyclerItemMovieName.text = movie.title
                mainFragmentRecyclerItemMovieVoteAverage.text =
                    String.format("Рейтинг: %s", movie.vote_average.toString())
                mainFragmentRecyclerItemMovieReleaseDate.text = movie.release_date
                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(movie)
                }
                Picasso
                    .get()
                    .load("https://image.tmdb.org/t/p/w500${movie.poster_path}?language=ru")
                    .transform(RectangleTransformation())
                    .into(moviePoster)
            }
        }
    }
}