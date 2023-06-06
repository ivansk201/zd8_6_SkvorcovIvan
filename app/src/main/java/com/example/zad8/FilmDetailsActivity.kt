package com.example.zad8


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FilmDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        val selectedFilm = intent.getParcelableExtra<Film>("film")

        selectedFilm?.let {
            val imageView: ImageView = findViewById(R.id.iv_film)
            imageView.setImageResource(it.imageResId)

            val titleTextView: TextView = findViewById(R.id.tv_title)
            titleTextView.text = it.title

            val genreTextView: TextView = findViewById(R.id.tv_genre)
            genreTextView.text = it.genre

            val authorTextView: TextView = findViewById(R.id.tv_autor)
            authorTextView.text = it.author

            val sumaryTextView: TextView = findViewById(R.id.tv_sumar)
            sumaryTextView.text = it.suammry
        }
    }
}
