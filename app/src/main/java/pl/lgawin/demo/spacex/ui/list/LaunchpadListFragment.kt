package pl.lgawin.demo.spacex.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lgawin.demo.spacex.R
import pl.lgawin.demo.spacex.databinding.FragmentLaunchpadListBinding

class LaunchpadListFragment : Fragment() {

    private var _binding: FragmentLaunchpadListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<LaunchpadListViewModel>()
    private val adapter = LaunchpadListAdapter {
        findNavController().navigate(R.id.action_launchpadListFragment_to_launchpadDetailsFragment)
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
        binding.launchpadList.adapter = adapter
        viewModel.launchpads.observe(viewLifecycleOwner, adapter::submitList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
