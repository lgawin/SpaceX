package pl.lgawin.demo.spacex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lgawin.demo.spacex.databinding.FragmentLaunchpadListBinding

class LaunchpadListFragment : Fragment() {

    private var _binding: FragmentLaunchpadListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModel<LaunchpadListViewModel>()
    private val adapter = LaunchpadListAdapter {
        Toast.makeText(requireContext(), "Show details: $it", Toast.LENGTH_SHORT).show()
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
