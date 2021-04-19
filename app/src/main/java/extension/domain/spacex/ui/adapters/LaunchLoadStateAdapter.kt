package extension.domain.spacex.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import extension.domain.spacex.databinding.LoadStateViewBinding

class LaunchLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LaunchLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.loadStateViewBinding.loadStateRetry.isVisible = loadState !is LoadState.Loading
        holder.loadStateViewBinding.loadStateErrorMessage.isVisible = loadState !is LoadState.Loading
        holder.loadStateViewBinding.loadStateProgress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            holder.loadStateViewBinding.loadStateErrorMessage.text = loadState.error.localizedMessage
        }

        holder.loadStateViewBinding.loadStateRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class LoadStateViewHolder(val loadStateViewBinding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(loadStateViewBinding.root)
}