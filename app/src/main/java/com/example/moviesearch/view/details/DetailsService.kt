package com.example.moviesearch.view.details

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors

const val MOVIE_ID = "MovieId"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(MOVIE_ID, 1)
            loadMovie(id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(movieId: Int) {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${movieId}?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru")

            lateinit var urlConnection: HttpURLConnection
            try {
                urlConnection = (uri.openConnection() as HttpURLConnection).apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                }

                val movieDTO: MovieDTO = Gson().fromJson(
                    getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                    MovieDTO::class.java
                )
                onResponse(movieDTO)

            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movieDTO: MovieDTO?) {
        if (movieDTO == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(
                movieDTO.title,
                movieDTO.overview,
                movieDTO.release_date,
                movieDTO.genres?.get(0)?.name
            )
        }
    }

    private fun onSuccessResponse(
        title: String?,
        overview: String?,
        release_date: String?,
        genres: String?
    ) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, title)
        broadcastIntent.putExtra(DETAILS_OVERVIEW_EXTRA, overview)
        broadcastIntent.putExtra(DETAILS_RELEASE_DATE_EXTRA, release_date)
        broadcastIntent.putExtra(DETAILS_GENRES_EXTRA, genres)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}