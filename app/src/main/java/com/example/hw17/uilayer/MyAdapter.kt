package com.example.hw17

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw17.databinding.MovieLayoutBinding
import com.example.hw17.localmodel.Movie

class MyAdapter(private val movieList: List<Movie>) : ListAdapter<Movie, MyAdapter.MovieViewHolder>(DiffCallback()) {
    private lateinit var viewClick: ViewClick


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val binding = MovieLayoutBinding.bind(itemView)
        private var pos = 0
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            viewClick.onClick(pos)
        }

        fun fill(position: Int) {
            pos = position
            val movie = movieList[position]
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500${movie.imagePath}").into(binding.imageView)
            binding.title.text = movie.title
            binding.title.isSelected = true
            binding.overview.text = movie.overview
            binding.voteAverage.text = "Voted " + movie.voteAverage + " of 10."
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.d("ali", "onCreateViewHolder: w2")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.fill(position)
    }

    interface ViewClick {
        fun onClick(pos: Int)
    }

    fun onViewClicked(viewClick: ViewClick) {
        this.viewClick = viewClick
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id && oldItem.voteAverage == newItem.voteAverage && oldItem.title == newItem.title && oldItem.overview == newItem.overview
    }

}