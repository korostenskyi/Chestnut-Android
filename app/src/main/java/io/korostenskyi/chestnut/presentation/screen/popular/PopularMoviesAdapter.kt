package io.korostenskyi.chestnut.presentation.screen.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.korostenskyi.chestnut.databinding.ItemPopularMovieBinding
import io.korostenskyi.chestnut.domain.model.Movie

class PopularMoviesAdapter : ListAdapter<Movie, PopularMoviesViewHolder>(MovieDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return ItemPopularMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { PopularMoviesViewHolder(it) }
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class PopularMoviesViewHolder(
    private val binding: ItemPopularMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.ivPoster.load(movie.posterPath)
    }
}

object MovieDiff : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
