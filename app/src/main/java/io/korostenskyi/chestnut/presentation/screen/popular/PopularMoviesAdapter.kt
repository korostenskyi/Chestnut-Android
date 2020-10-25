package io.korostenskyi.chestnut.presentation.screen.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.korostenskyi.chestnut.databinding.ItemPopularMovieBinding
import io.korostenskyi.chestnut.domain.model.Movie

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesViewHolder>() {

    private val popularMovies = mutableListOf<Movie>()

    override fun getItemCount() = popularMovies.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return ItemPopularMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { PopularMoviesViewHolder(it) }
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(popularMovies[position])
    }

    fun addMovies(list: List<Movie>) {
        popularMovies.addAll(list)
        notifyDataSetChanged()
    }
}

class PopularMoviesViewHolder(
    private val binding: ItemPopularMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.apply {
            ivPoster.load("$POSTER_BASE_URL${movie.posterPath}")
        }
    }

    companion object {
        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185_and_h278_bestv2"
    }
}
