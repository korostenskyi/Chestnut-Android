package io.korostenskyi.chestnut.presentation.screen.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.databinding.FragmentMovieDetailsBinding
import io.korostenskyi.chestnut.extensions.viewBindings
import io.korostenskyi.chestnut.presentation.base.ui.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details) {

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val binding by viewBindings(FragmentMovieDetailsBinding::bind)
    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        collectFlows()
        viewModel.retrieveMovieDetails(args.movieId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroy()
    }

    private fun collectFlows() {
        viewModel.detailsFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun render(state: MovieDetailsState) {
        when (state) {
            is MovieDetailsState.Loading -> {}
            is MovieDetailsState.Success -> {}
            is MovieDetailsState.Error -> {}
        }
    }

    private fun setupViews() {
    }
}
