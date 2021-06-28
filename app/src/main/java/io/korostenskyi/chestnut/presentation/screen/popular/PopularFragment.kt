package io.korostenskyi.chestnut.presentation.screen.popular

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.databinding.FragmentPopularBinding
import io.korostenskyi.chestnut.extensions.viewBindings
import io.korostenskyi.chestnut.presentation.base.ui.BaseFragment
import io.korostenskyi.chestnut.presentation.screen.popular.adapter.PopularMoviesAdapter
import io.korostenskyi.chestnut.presentation.screen.popular.adapter.PopularMoviesStateAdapter
import io.korostenskyi.chestnut.util.delegate.RouterDelegate
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : BaseFragment(R.layout.fragment_popular) {

    private val binding by viewBindings(FragmentPopularBinding::bind)
    private val router by RouterDelegate()
    private val viewModel by viewModels<PopularViewModel>()

    @Inject lateinit var imageLoader: ImageLoader

    private val popularMoviesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PopularMoviesAdapter(imageLoader, onItemClick = { movie ->
            router.fromPopularToMovieDetails(movie.id)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        collectFlows()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroy()
    }

    private fun collectFlows() {
        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.moviesFlow.collectLatest(popularMoviesAdapter::submitData)
        }
    }

    private fun setupViews() {
        setupPopularMoviesRecyclerView()
    }

    private fun setupPopularMoviesRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvMovies.apply {
            adapter = popularMoviesAdapter.withLoadStateHeaderAndFooter(
                header = PopularMoviesStateAdapter(),
                footer = PopularMoviesStateAdapter()
            )
            layoutManager = gridLayoutManager
        }
        popularMoviesAdapter.addLoadStateListener { state ->
            binding.rvMovies.isVisible = state.refresh != LoadState.Loading
            binding.pbLoading.isVisible = state.refresh == LoadState.Loading
        }
    }
}
