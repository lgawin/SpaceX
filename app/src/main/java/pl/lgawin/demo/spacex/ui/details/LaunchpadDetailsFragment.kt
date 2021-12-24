package pl.lgawin.demo.spacex.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
            with(binding) {
                name.text = it.name
                description.text = it.description
                lblStatus.visible = it.status != null
                status.text = it.status
                lblLocation.visible = it.status != null
                location.text = it.location
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private var View.visible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }
