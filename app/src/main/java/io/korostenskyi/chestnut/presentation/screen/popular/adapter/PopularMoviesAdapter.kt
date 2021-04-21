package io.korostenskyi.chestnut.presentation.screen.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.korostenskyi.chestnut.databinding.ItemPopularMovieBinding
import io.korostenskyi.chestnut.domain.model.Movie

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesViewHolder>() {

    private val _items = mutableListOf<Movie>()

    val items: List<Movie>
        get() = _items

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return ItemPopularMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { PopularMoviesViewHolder(it) }
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
    private val binding: ItemPopularMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.ivPoster.load(movie.posterPath)
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
