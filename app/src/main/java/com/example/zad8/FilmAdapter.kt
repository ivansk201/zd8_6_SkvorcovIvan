package com.example.zad8
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class FilmAdapter(context: Context, films: ArrayList<Film>) :
    ArrayAdapter<Film>(context, 0, films) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false)
        }

        val currentFilm = getItem(position)

        val titleTextView = itemView?.findViewById<TextView>(R.id.titleTextView)
        val genreTextView = itemView?.findViewById<TextView>(R.id.genreTextView)
        val imageView = itemView?.findViewById<ImageView>(R.id.imageView)

        titleTextView?.text = currentFilm?.title
        genreTextView?.text = currentFilm?.genre
        imageView?.setImageResource(currentFilm?.imageResId?.toInt() ?: 0)


        return itemView!!
    }
}