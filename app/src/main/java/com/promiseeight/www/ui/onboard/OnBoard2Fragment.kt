import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentOnBoard2Binding
import com.promiseeight.www.ui.common.BaseFragment

class OnBoard2Fragment : BaseFragment<FragmentOnBoard2Binding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoard2Binding {
        return FragmentOnBoard2Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager_onboard)
        binding.btnOnboard2.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = OnBoard2Fragment()
    }
}