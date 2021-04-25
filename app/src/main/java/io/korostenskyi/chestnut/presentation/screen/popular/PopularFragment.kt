package io.korostenskyi.chestnut.presentation.screen.popular

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.databinding.FragmentPopularBinding
import io.korostenskyi.chestnut.extensions.viewBindings
import io.korostenskyi.chestnut.presentation.base.ui.BaseFragment
import io.korostenskyi.chestnut.presentation.screen.popular.adapter.PaginationListener
import io.korostenskyi.chestnut.presentation.screen.popular.adapter.PopularMoviesAdapter
import io.korostenskyi.chestnut.util.RouterDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : BaseFragment(R.layout.fragment_popular) {

    private val binding by viewBindings(FragmentPopularBinding::bind)
    private val router by RouterDelegate()
    private val viewModel by viewModels<PopularViewModel>()

    @Inject lateinit var imageLoader: ImageLoader

    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        collectFlows()
        viewModel.loadMore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroy()
    }

    private fun collectFlows() {
        viewModel.moviesStateFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> {
                binding.pbLoading.isVisible = true
            }
            is MoviesState.Success -> {
                binding.pbLoading.isVisible = false
                popularMoviesAdapter.replaceAll(state.movies)
            }
            is MoviesState.Failure -> {
                binding.pbLoading.isVisible = false
            }
        }
    }

    private fun setupViews() {
        setupPopularMoviesRecyclerView()
    }

    private fun setupPopularMoviesRecyclerView() {
        popularMoviesAdapter = PopularMoviesAdapter(imageLoader, onItemClick = { movie ->
            router.fromPopularToMovieDetails(movie.id)
        })
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvMovies.apply {
            adapter = popularMoviesAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(object : PaginationListener(gridLayoutManager) {

                override fun isLoading() = viewModel.moviesStateFlow.value is MoviesState.Loading

                override fun loadMoreItems() = viewModel.loadMore()

                override fun pageSize() = popularMoviesAdapter.itemCount
            })
        }
    }
}
