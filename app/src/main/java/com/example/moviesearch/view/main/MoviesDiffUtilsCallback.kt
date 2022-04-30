package com.example.moviesearch.view.main

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesearch.model.Movie

class MoviesDiffUtilsCallback(private val oldList: List<Movie>, private val newList: List<Movie>) :
    DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie: Movie = oldList[oldItemPosition]
        val newMovie: Movie = newList[newItemPosition]
        return oldMovie.id == newMovie.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldMovie: Movie = oldList[oldItemPosition]
        val newMovie: Movie = newList[newItemPosition]

        return oldMovie.title == newMovie.title &&
                oldMovie.release_date == newMovie.release_date &&
                oldMovie.genre == newMovie.genre &&
                oldMovie.poster_path == newMovie.poster_path
    }
}