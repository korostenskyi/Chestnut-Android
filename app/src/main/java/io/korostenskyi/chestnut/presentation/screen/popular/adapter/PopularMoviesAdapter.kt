package io.korostenskyi.chestnut.presentation.screen.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import io.korostenskyi.chestnut.databinding.ItemPopularMovieBinding
import io.korostenskyi.chestnut.domain.model.Movie

class PopularMoviesAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<PopularMoviesViewHolder>() {

    private val _items = mutableListOf<Movie>()

    val items: List<Movie>
        get() = _items

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return ItemPopularMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { PopularMoviesViewHolder(it, imageLoader, onItemClick) }
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItems(newItems: List<Movie>) {
        val range = items.count()..newItems.lastIndex
        _items.addAll(newItems)
        notifyItemRangeChanged(range.first, range.last)
    }

    fun replaceAll(newItems: List<Movie>) {
        val diffResult = MovieDiff(items, newItems)
                .let { DiffUtil.calculateDiff(it) }
        _items.apply {
            clear()
            addAll(newItems)
        }
        diffResult.dispatchUpdatesTo(this)
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

    fun bind(movie: Movie) {
        _movie = movie
        setupPoster(movie.posterPath)
    }

    private fun setupPoster(url: String?) {
        val request = ImageRequest.Builder(binding.root.context)
            .data(url)
            .target(binding.ivPoster)
            .build()
        imageLoader.enqueue(request)
    }
}

class MovieDiff(
    private val oldItems: List<Movie>,
    private val newItems: List<Movie>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.count()

    override fun getNewListSize() = newItems.count()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.title.equals(newItem.title, ignoreCase = true)
                && oldItem.description.equals(newItem.description, ignoreCase = true)
                && oldItem.isAdult == newItem.isAdult
    }
}
