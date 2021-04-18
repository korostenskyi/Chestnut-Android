package io.korostenskyi.chestnut.presentation.screen.popular

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.databinding.FragmentPopularBinding
import io.korostenskyi.chestnut.extensions.launch
import io.korostenskyi.chestnut.extensions.viewBindings
import io.korostenskyi.chestnut.presentation.base.ui.BaseFragment
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
        viewModel.popularMoviesFlow
            .onEach(popularMoviesAdapter::submitList)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupViews() {
        setupPopularMoviesRecyclerView()
    }

    private fun setupPopularMoviesRecyclerView() {
        binding.rvMovies.apply {
            adapter = popularMoviesAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }
}
