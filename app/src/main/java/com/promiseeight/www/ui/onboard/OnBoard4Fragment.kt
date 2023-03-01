import android.graphics.Path.Direction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentOnBoard4Binding
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.onboard.OnBoardingFragmentDirections

class OnBoard4Fragment : BaseFragment<FragmentOnBoard4Binding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoard4Binding {
        return FragmentOnBoard4Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager_onboard)
        binding.btnOnboard4.setOnClickListener {
            viewPager?.currentItem = 4
            requireParentFragment().findNavController().navigate(
                OnBoardingFragmentDirections.actionFragmentOnBoardingToFragmentHome()
            )

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = OnBoard4Fragment()
    }
}