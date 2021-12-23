package pl.lgawin.demo.spacex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.lgawin.demo.spacex.domain.LaunchpadModel

class LaunchpadListAdapter(private val onClick: (LaunchpadModel) -> Unit) :
    ListAdapter<LaunchpadModel, SimpleLaunchpadViewHolder>(LaunchpadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleLaunchpadViewHolder =
        SimpleLaunchpadViewHolder.inflateIn(parent).apply {
            clickListener = onClick
        }

    override fun onBindViewHolder(holder: SimpleLaunchpadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class LaunchpadDiffCallback : DiffUtil.ItemCallback<LaunchpadModel>() {
    override fun areItemsTheSame(oldItem: LaunchpadModel, newItem: LaunchpadModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LaunchpadModel, newItem: LaunchpadModel): Boolean =
        oldItem == newItem
}

class SimpleLaunchpadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var clickListener: (LaunchpadModel) -> Unit = {}

    private val name = itemView.findViewById<TextView>(android.R.id.text1)

    fun bind(item: LaunchpadModel) {
        name.text = item.name
        itemView.setOnClickListener { clickListener(item) }
    }

    companion object {

        fun inflateIn(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
                .let { SimpleLaunchpadViewHolder(it) }
    }
}
