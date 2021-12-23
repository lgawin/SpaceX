package pl.lgawin.demo.spacex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.lgawin.demo.spacex.domain.LaunchpadModel

class LaunchpadListAdapter :
    ListAdapter<LaunchpadModel, LaunchpadViewHolder>(LaunchpadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchpadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return LaunchpadViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaunchpadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class LaunchpadDiffCallback : DiffUtil.ItemCallback<LaunchpadModel>() {
    override fun areItemsTheSame(oldItem: LaunchpadModel, newItem: LaunchpadModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LaunchpadModel, newItem: LaunchpadModel): Boolean =
        oldItem == newItem
}

class LaunchpadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name = itemView.findViewById<TextView>(android.R.id.text1)

    fun bind(item: LaunchpadModel) {
        name.text = item.name
    }
}
