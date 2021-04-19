package extension.domain.spacex.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import extension.domain.spacex.R
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.databinding.LaunchItemBinding
import extension.domain.spacex.databinding.LaunchItemEndFooterBinding
import extension.domain.spacex.ui.main.LaunchUIModel
import extension.domain.spacex.utils.extensions.toDateStamp

class LaunchAdapter(val onItemSelected: ((Launch?) -> Unit)? = null) :
    PagingDataAdapter<LaunchUIModel, RecyclerView.ViewHolder>(
        LaunchUIModelComparator
    ) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val launchUIModel = getItem(position)

        launchUIModel?.let {
            when (launchUIModel) {
                is LaunchUIModel.LaunchItem -> {
                    val viewHolder = holder as LaunchViewHolder

                    viewHolder.binding.launchBox.setOnClickListener {
                        onItemSelected?.invoke(launchUIModel.launch)
                    }

                    viewHolder.binding.name.text = launchUIModel.launch.name
                    viewHolder.binding.launchDate.text = String.format(
                        viewHolder.itemView.context.getString(R.string.launch_date_xx),
                        launchUIModel.launch.date_utc?.toDateStamp()
                    )

                    viewHolder.binding.thumbnail.setImageResource(R.color.veryLightGreyPlaceholder)
                    Glide.with(viewHolder.itemView.context)
                        .load(launchUIModel.launch.links?.patch?.small)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.color.veryLightGreyPlaceholder)
                        .error(R.drawable.ic_baseline_airplanemode_closest_to_rocket)
                        .dontAnimate()
                        .into(viewHolder.binding.thumbnail)

                    viewHolder.binding.missionSuccessIcon.setImageResource(if (launchUIModel.launch.success == true) R.drawable.ic_baseline_tick else R.drawable.ic_baseline_cross)
                }
                is LaunchUIModel.EndFooterItem -> {
                    val viewHolder = holder as LaunchEndFooterViewHolder
                    viewHolder.binding
                        .endFooterBox.setOnClickListener {
                            onItemSelected?.invoke(null)
                        }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is LaunchUIModel.LaunchItem -> ItemViewTypes.ITEM.value
            is LaunchUIModel.EndFooterItem -> ItemViewTypes.FOOTER.value
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemViewTypes.ITEM.value -> {
                LaunchViewHolder(
                    LaunchItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                LaunchEndFooterViewHolder(
                    LaunchItemEndFooterBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    class LaunchViewHolder(val binding: LaunchItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class LaunchEndFooterViewHolder(val binding: LaunchItemEndFooterBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val LaunchUIModelComparator = object : DiffUtil.ItemCallback<LaunchUIModel>() {
            override fun areItemsTheSame(oldItem: LaunchUIModel, newItem: LaunchUIModel): Boolean {
                return (oldItem is LaunchUIModel.LaunchItem && newItem is LaunchUIModel.LaunchItem &&
                        oldItem.launch.id == newItem.launch.id) ||
                        (oldItem is LaunchUIModel.EndFooterItem && newItem is LaunchUIModel.EndFooterItem)
            }

            override fun areContentsTheSame(
                oldItem: LaunchUIModel,
                newItem: LaunchUIModel
            ): Boolean =
                oldItem == newItem
        }


        enum class ItemViewTypes(val value: Int) {
            ITEM(0), FOOTER(1);
        }
    }

}