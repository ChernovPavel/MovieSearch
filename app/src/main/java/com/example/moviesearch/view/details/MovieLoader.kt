package com.example.moviesearch.view.details

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors

@RequiresApi(Build.VERSION_CODES.N)
class MovieLoader(
    private val listener: MovieLoaderListener,
    private val id: Int
) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${id}?api_key=5cc3b6d0f441200e25ccf12162000327&language=ru")
            val handler = Handler()

            Thread {
                lateinit var urlConnection: HttpURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    urlConnection.addRequestProperty("api_key", BuildConfig.MOVIE_API_KEY)

                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val response = getLines(bufferedReader)
                    val movieDTO: MovieDTO = Gson().fromJson(response, MovieDTO::class.java)

                    handler.post {
                        listener.onLoaded(movieDTO)
                    }

                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {

                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieLoaderListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable)
    }
}