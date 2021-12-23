package pl.lgawin.demo.spacex.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pl.lgawin.demo.spacex.domain.LaunchpadModel

class LaunchpadListAdapter(private val onClick: (LaunchpadModel) -> Unit) :
    ListAdapter<LaunchpadModel, LaunchpadViewHolder>(LaunchpadModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchpadViewHolder =
        LaunchpadViewHolder(parent).apply { clickListener = onClick }

    override fun onBindViewHolder(holder: LaunchpadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
