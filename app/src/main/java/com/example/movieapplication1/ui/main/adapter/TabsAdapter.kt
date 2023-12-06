package com.example.movieapplication.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication1.data.model.Result
import com.example.movieapplication.utils.Utils
import com.example.movieapplication.utils.Utils.capitalizeWords
import com.example.movieapplication1.R

class TabsAdapter(private val context: Context, private var result: ArrayList<Result>) :
    RecyclerView.Adapter<TabsAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_slider_list, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: TabsAdapter.ModelViewHolder, position: Int) {
        val item = result[position]

        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + item.poster_path)
            .placeholder(R.drawable.ic_default_icon)
            .into(holder.imgMovie)
        item.overview.let { holder.movieDescription.text = it }
        item.title.let { holder.movieTitle.text = it }

        item.title.let { title ->
            var movieTitle = title?.capitalizeWords()
            item.release_date.let { releaseDate ->
                movieTitle += " ${"(" + releaseDate?.let { Utils.getFormattedYear(it) } + ")".capitalizeWords()}"
            }
            holder.movieTitle.text = movieTitle
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgMovie: ImageView
        var movieDescription: TextView
        var movieTitle: TextView

        init {
            imgMovie = itemView.findViewById(R.id.img_movie)
            movieDescription = itemView.findViewById(R.id.txt_movie_description)
            movieTitle = itemView.findViewById(R.id.txt_movie_title)
        }
    }
}
