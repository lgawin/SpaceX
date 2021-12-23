package pl.lgawin.demo.spacex.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.lgawin.demo.spacex.R
import pl.lgawin.demo.spacex.databinding.FragmentLaunchpadDetailsBinding

class LaunchpadDetailsFragment : Fragment(R.layout.fragment_launchpad_details) {

    private var _binding: FragmentLaunchpadDetailsBinding? = null
    private val binding get() = _binding!!

    private val navArgs by navArgs<LaunchpadDetailsFragmentArgs>()
    private val viewModel by viewModel<LaunchpadDetailsViewModel> { parametersOf(navArgs.id, navArgs.name) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLaunchpadDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.details.observe(viewLifecycleOwner) {
            binding.name.text = it.name
            binding.description.text = it.description
            binding.status.text = it.status
            binding.location.text = it.location
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
