package io.korostenskyi.chestnut.presentation.screen.popular.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.korostenskyi.chestnut.databinding.ItemErrorBinding
import io.korostenskyi.chestnut.databinding.ItemLoadingBinding

class PopularMoviesStateAdapter : LoadStateAdapter<LoadStateItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: LoadStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateItemViewHolder {
        return when (loadState) {
            LoadState.Loading -> ProgressViewHolder(ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            is LoadState.Error -> ErrorViewHolder(ItemErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {
        private const val ERROR = 1
        private const val PROGRESS = 0
    }
}

abstract class LoadStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(loadState: LoadState)
}

class ProgressViewHolder internal constructor(
    binding: ItemLoadingBinding
) : LoadStateItemViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {
    }
}

class ErrorViewHolder internal constructor(
    private val binding: ItemErrorBinding
) : LoadStateItemViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {
        require(loadState is LoadState.Error)
        binding.tvErrorMessage.text = loadState.error.localizedMessage
    }
}
