package com.example.moviesearch.view.details

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.model.ListMoviesDTO
import com.example.moviesearch.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors

@RequiresApi(Build.VERSION_CODES.N)
class ListMoviesLoader(
    private val listener: ListMoviesLoaderListener,
) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadListMovies() =
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/top_rated?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU&page=1&region=ru")
            val handler = Handler()

            Thread {
                lateinit var urlConnection: HttpURLConnection
                try {
                    urlConnection = (uri.openConnection() as HttpURLConnection).apply {
                        requestMethod = "GET"
                        readTimeout = 10000
                        addRequestProperty("api_key", BuildConfig.MOVIE_API_KEY)
                    }

                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val response = getLines(bufferedReader)
                    val listMoviesDTO: ListMoviesDTO = Gson().fromJson(response, ListMoviesDTO::class.java)

                    handler.post {
                        listener.onLoadedList(listMoviesDTO)
                    }

                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailedList(e)
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailedList(e)
        }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface ListMoviesLoaderListener {
        fun onLoadedList(listMoviesDTO: ListMoviesDTO)
        fun onFailedList(throwable: Throwable)
    }
}