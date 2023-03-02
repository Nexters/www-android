import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentOnBoard1Binding
import com.promiseeight.www.ui.common.BaseFragment

class OnBoardFirstFragment : BaseFragment<FragmentOnBoard1Binding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoard1Binding {
        return FragmentOnBoard1Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager_onboard)
        binding.btnOnboard1.setOnClickListener {
            viewPager?.currentItem = 1
        }
    }

}