import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentOnBoard3Binding
import com.promiseeight.www.ui.common.BaseFragment

class OnBoard3Fragment : BaseFragment<FragmentOnBoard3Binding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoard3Binding {
        return FragmentOnBoard3Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager_onboard)
        binding.btnOnboard3.setOnClickListener {
            viewPager?.currentItem = 3
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = OnBoard3Fragment()
    }
}