package pl.lgawin.demo.spacex.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.lgawin.demo.spacex.databinding.LaunchpadListItemBinding
import pl.lgawin.demo.spacex.domain.LaunchpadModel

class LaunchpadViewHolder(private val binding: LaunchpadListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var clickListener: (LaunchpadModel) -> Unit = {}

    fun bind(item: LaunchpadModel) = with(binding) {
        name.text = item.name
        root.setOnClickListener { clickListener(item) }
    }

    companion object {

        operator fun invoke(parent: ViewGroup): LaunchpadViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LaunchpadListItemBinding.inflate(inflater, parent, false)
            return LaunchpadViewHolder(binding)
        }
    }
}
