package io.korostenskyi.chestnut.presentation.screen.popular

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.databinding.FragmentPopularBinding
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.extensions.launch
import io.korostenskyi.chestnut.extensions.viewBindings
import io.korostenskyi.chestnut.presentation.base.ui.BaseFragment
import io.korostenskyi.chestnut.presentation.screen.popular.adapter.PaginationListener
import io.korostenskyi.chestnut.presentation.screen.popular.adapter.PopularMoviesAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PopularFragment : BaseFragment(R.layout.fragment_popular) {

    private val binding by viewBindings(FragmentPopularBinding::bind)
    private val popularMoviesAdapter = PopularMoviesAdapter()
    private val viewModel by viewModels<PopularViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        collectFlows()
        viewModel.retrievePopularMovies()
    }

    private fun collectFlows() = launch {
        viewModel.moviesStateFlow
            .onEach(::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> {
                binding.rvMovies.isInvisible = true
            }
            is MoviesState.Success -> {
                binding.rvMovies.isInvisible = false
                popularMoviesAdapter.submitList(state.movies)
            }
            is MoviesState.Failure -> {}
        }
    }

    private fun setupViews() {
        setupPopularMoviesRecyclerView()
    }

    private fun setupPopularMoviesRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvMovies.apply {
            adapter = popularMoviesAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(object : PaginationListener(gridLayoutManager) {

                override fun isLoading() = viewModel.moviesStateFlow.value is MoviesState.Loading

                override fun loadMoreItems() {
                    viewModel.page += 1
                }

                override fun pageSize() = popularMoviesAdapter.itemCount
            })
        }
    }
}
