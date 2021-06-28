package io.korostenskyi.chestnut.presentation.screen.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import io.korostenskyi.chestnut.databinding.ItemPopularMovieBinding
import io.korostenskyi.chestnut.domain.model.Movie

class PopularMoviesAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, PopularMoviesViewHolder>(MovieDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return ItemPopularMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { PopularMoviesViewHolder(it, imageLoader, onItemClick) }
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PopularMoviesViewHolder(
    private val binding: ItemPopularMovieBinding,
    private val imageLoader: ImageLoader,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var _movie: Movie? = null

    init {
        binding.ivPoster.setOnClickListener {
            _movie?.let(onItemClick)
        }
    }

    fun bind(movie: Movie?) {
        _movie = movie
        setupPoster(movie?.posterPath)
    }

    private fun setupPoster(url: String?) {
        val request = ImageRequest.Builder(binding.root.context)
            .data(url)
            .target(binding.ivPoster)
            .build()
        imageLoader.enqueue(request)
    }
}

private object MovieDiff : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title.equals(newItem.title, ignoreCase = true)
                && oldItem.description.equals(newItem.description, ignoreCase = true)
                && oldItem.isAdult == newItem.isAdult
    }
}
