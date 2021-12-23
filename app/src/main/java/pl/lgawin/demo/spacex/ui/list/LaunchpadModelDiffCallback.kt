package pl.lgawin.demo.spacex.ui.list

import androidx.recyclerview.widget.DiffUtil
import pl.lgawin.demo.spacex.domain.LaunchpadModel

class LaunchpadModelDiffCallback : DiffUtil.ItemCallback<LaunchpadModel>() {
    override fun areItemsTheSame(oldItem: LaunchpadModel, newItem: LaunchpadModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LaunchpadModel, newItem: LaunchpadModel): Boolean =
        oldItem == newItem
}
