package com.example.mobilliumcase.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.databinding.ItemLoadStateBinding

class BaseLoadStateAdapter (private val retry: () -> Unit): LoadStateAdapter<BaseLoadStateAdapter.BaseLoadStateViewHolder>() {
    //region Field
    //endregion Field

    //region Constructor
    //endregion Constructor

    //region Public Method
    override fun onBindViewHolder(holder: BaseLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BaseLoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return BaseLoadStateViewHolder(binding)
    }
    //endregion Public Method

    //region Private Method
    inner class BaseLoadStateViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
    //endregion Private Method
}