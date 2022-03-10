package com.example.moviesearch.utils

import android.graphics.*
import com.squareup.picasso.Transformation

class RectangleTransformation : Transformation {
    override fun transform(source: Bitmap): Bitmap {

        val path = Path()

        path.addRoundRect(
            RectF(0F, (source.height).toFloat(), (source.width).toFloat(), 0F),
            (source.width).toFloat() / 8F,
            (source.width).toFloat() / 8f,
            Path.Direction.CCW
        )

        val answerBitMap =
            Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(answerBitMap)
        canvas.clipPath(path)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(source, 0f, 0f, paint)
        source.recycle()
        return answerBitMap
    }

    override fun key(): String {
        return "rectangle"
    }
}