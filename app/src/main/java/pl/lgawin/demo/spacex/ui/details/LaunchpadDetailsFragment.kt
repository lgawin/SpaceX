package pl.lgawin.demo.spacex.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lgawin.demo.spacex.R
import pl.lgawin.demo.spacex.databinding.FragmentLaunchpadDetailsBinding

class LaunchpadDetailsFragment : Fragment(R.layout.fragment_launchpad_details) {

    private var _binding: FragmentLaunchpadDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<LaunchpadDetailsViewModel>()

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
//        TODO use
        viewModel.hashCode()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
