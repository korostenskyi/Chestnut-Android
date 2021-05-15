package io.korostenskyi.chestnut.presentation.screen.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.databinding.FragmentMovieDetailsBinding
import io.korostenskyi.chestnut.di.DiNames
import io.korostenskyi.chestnut.domain.model.MovieDetails
import io.korostenskyi.chestnut.extensions.viewBindings
import io.korostenskyi.chestnut.presentation.base.ui.BaseFragment
import io.korostenskyi.chestnut.util.RouterDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details) {

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val binding by viewBindings(FragmentMovieDetailsBinding::bind)
    private val router by RouterDelegate()
    private val viewModel by viewModels<MovieDetailsViewModel>()

    @Inject @Named(DiNames.SOFTWARE_IMAGE_LOADER) lateinit var imageLoader: ImageLoader

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
            }
            is MovieDetailsState.Success -> {
                binding.pbLoading.isVisible = false
                setupMovie(state.movie)
            }
            is MovieDetailsState.Error -> {
                binding.pbLoading.isVisible = false
            }
        }
    }

    private fun setupMovie(movie: MovieDetails) {
        setupBackdrop(movie.backdropPath)
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
            .target(
                onSuccess = { drawable ->
                    val bitmap = drawable.toBitmap()
                    Palette.from(bitmap).generate { palette ->
                        palette?.getDominantColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.colorPrimary
                            )
                        )?.let { color ->
                            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
                            val contentColor = if (darkness < 0.5) ContentTint.LIGHT else ContentTint.DARK
                            setupConstraintSet(contentColor)
                        }
                    }
                    binding.ivBackdrop.setImageDrawable(drawable)
                }
            )
            .build()
        imageLoader.enqueue(request)
    }

    private fun setupConstraintSet(tint: ContentTint) {

        binding.root.apply {
            getConstraintSet(R.id.start)?.let { constraintSet ->

                val color = when (tint) {
                    ContentTint.DARK -> ContextCompat.getColor(requireContext(), android.R.color.white)
                    ContentTint.LIGHT -> ContextCompat.getColor(requireContext(), android.R.color.black)
                }
                constraintSet.setColorValue(R.id.iv_back, "ColorFilter", color)
                constraintSet.setColorValue(R.id.tv_title, "ColorFilter", color)
            }
            getConstraintSet(R.id.end)?.let { constraintSet ->
                val color = when (tint) {
                    ContentTint.DARK -> ContextCompat.getColor(requireContext(), android.R.color.white)
                    ContentTint.LIGHT -> ContextCompat.getColor(requireContext(), android.R.color.black)
                }
                constraintSet.setColorValue(R.id.iv_back, "ColorFilter", color)
                constraintSet.setColorValue(R.id.tv_title, "ColorFilter", color)
            }
        }
    }

    private fun setupViews() {
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            router.back()
        }
    }
}

enum class ContentTint {
    DARK, LIGHT
}
