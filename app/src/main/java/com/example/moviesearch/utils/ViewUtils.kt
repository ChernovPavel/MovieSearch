package com.example.moviesearch.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

const val SERVER_ERROR = "Ошибка сервера"
const val CORRUPTED_DATA = "Неполные данные"
const val IS_RUSSIAN_LANGUAGE = "IS_RUSSIAN_LANGUAGE"

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length)
        .setAction(actionText, action)
        .show()
}