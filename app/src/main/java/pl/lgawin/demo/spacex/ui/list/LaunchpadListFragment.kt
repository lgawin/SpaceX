package pl.lgawin.demo.spacex.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lgawin.demo.spacex.databinding.FragmentLaunchpadListBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager


class LaunchpadListFragment : Fragment() {

    private var _binding: FragmentLaunchpadListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<LaunchpadListViewModel>()
    private val launchpadListAdapter = LaunchpadListAdapter {
        LaunchpadListFragmentDirections.actionLaunchpadListToLaunchpadDetails(it.id, it.name)
            .navigateTo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLaunchpadListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.launchpadList) {
            adapter = launchpadListAdapter
            (layoutManager as? LinearLayoutManager)?.let {
                addItemDecoration(DividerItemDecoration(context, it.orientation))
            }
        }
        viewModel.launchpads.observe(viewLifecycleOwner, launchpadListAdapter::submitList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun NavDirections.navigateTo() = findNavController().navigate(this)
}
