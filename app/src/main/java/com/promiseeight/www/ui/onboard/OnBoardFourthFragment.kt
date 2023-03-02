import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentOnBoard4Binding
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.onboard.OnBoardingFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

class OnBoardFourthFragment : BaseFragment<FragmentOnBoard4Binding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoard4Binding {
        return FragmentOnBoard4Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOnboard4.setOnClickListener {
            findNavController().navigate(
                OnBoardingFragmentDirections.actionFragmentOnBoardingToFragmentHome()
            )

        }
    }

}