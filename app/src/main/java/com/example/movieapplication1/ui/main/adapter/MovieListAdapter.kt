package com.example.movieapplication1.ui.main.adapter

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
import com.example.movieapplication1.R

class MovieListAdapter(
    private val context: Context,
    private var result: ArrayList<Result>,
    private val listener: MovieListener,
) :
    RecyclerView.Adapter<MovieListAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_list, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val item = result[position]

        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + item.poster_path)
            .placeholder(R.drawable.ic_default_icon)
            .into(holder.imgMovie)
        item.title.let { holder.movieTitle.text = it }
        item.overview.let { holder.movieDescription.text = it }
        item.release_date.let { release_date ->
            holder.movieDate.text = release_date?.let { Utils.getFormattedDate(it) }
        }
        holder.itemView.setOnClickListener {
            item.id?.let { it1 -> listener.onMovieClicked(it1) }
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgMovie: ImageView
        var movieTitle: TextView
        var movieDescription: TextView
        var movieDate: TextView

        init {
            imgMovie = itemView.findViewById(R.id.img_movie)
            movieTitle = itemView.findViewById(R.id.txt_movie_title)
            movieDescription = itemView.findViewById(R.id.txt_movie_description)
            movieDate = itemView.findViewById(R.id.txt_date)
        }
    }
}

interface MovieListener {
    fun onMovieClicked(movieId: Int)
}