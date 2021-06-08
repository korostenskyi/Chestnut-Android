package io.korostenskyi.chestnut.presentation.screen.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.databinding.FragmentMovieDetailsBinding
import io.korostenskyi.chestnut.domain.model.MovieDetails
import io.korostenskyi.chestnut.extensions.viewBindings
import io.korostenskyi.chestnut.presentation.base.ui.BaseFragment
import io.korostenskyi.chestnut.util.delegate.RouterDelegate
import io.korostenskyi.chestnut.util.ui.IntentActionUtil
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details) {

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val binding by viewBindings(FragmentMovieDetailsBinding::bind)
    private val router by RouterDelegate()
    private val viewModel by viewModels<MovieDetailsViewModel>()

    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var actionUtil: IntentActionUtil

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
            is MovieDetailsState.Loading -> {
                binding.pbLoading.isVisible = true
                binding.toolbar.ivShare.isVisible = false
            }
            is MovieDetailsState.Success -> {
                binding.pbLoading.isVisible = false
                setupMovie(state.movie)
            }
            is MovieDetailsState.Error -> {
                binding.pbLoading.isVisible = false
                binding.toolbar.ivShare.isVisible = false
                binding.includeError.root.isVisible = true
                binding.includeError.tvErrorMessage.text = state.message
            }
        }
    }

    private fun setupMovie(movie: MovieDetails) {
        setupBackdrop(movie.backdropPath)
        setupShareButton(movie)
        setupRating(movie.voteAverage)
        binding.apply {
            tvTitle.text = movie.title
            if (movie.overview != null) {
                tvDescription.text = movie.overview
            } else {
                tvDescription.isVisible = false
            }
        }
    }

    private fun setupBackdrop(url: String?) {
        val request = ImageRequest.Builder(requireContext())
            .data(url)
            .target(binding.ivBackdrop)
            .build()
        imageLoader.enqueue(request)
    }

    private fun setupRating(rating: Double) {
        binding.tvRating.text = getString(R.string.movie_details_rating_average, rating)
    }

    private fun setupViews() {
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.toolbar.ivBack.setOnClickListener {
            router.back()
        }
    }

    private fun setupShareButton(movie: MovieDetails) {
        binding.toolbar.ivShare.isVisible = true
        binding.toolbar.ivShare.setOnClickListener {
            actionUtil.share(movie.title)
        }
    }
}
