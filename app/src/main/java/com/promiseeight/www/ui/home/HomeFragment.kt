package com.promiseeight.www.ui.home

import android.animation.ObjectAnimator
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.animation.AlphaAnimation
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentHomeBinding
import com.promiseeight.www.ui.adapter.HomeTabAdapter
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel : HomeViewModel by viewModels()

    private var homeTabAdapter : HomeTabAdapter? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.getMeetings()
        viewModel.setIsFirstFalse()

        setStatusBarColor(R.color.gray_100)

        binding.btn1FloatingMain.setOnClickListener{
            viewModel.updateFabState()

        }

        binding.btn3FloatingMain.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentAddMeeting()
            )
            viewModel.updateFabState(false)
        }

        binding.btn2FloatingMain.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentJoinMeeting()
            )
            viewModel.updateFabState(false)
        }
//
//        binding.btn2FloatingMain.animation = AlphaAnimation(0f,1f).apply {
//            duration = 500
//        }
//
//        binding.btn3FloatingMain.animation = AlphaAnimation(0f,1f).apply {
//            duration = 500
//        }

        //hideExtendedFab()


        binding.ivSetting.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentSetting()

            )
            viewModel.updateFabState(false)
        }

        binding.let{
            initViewPager(it.vpHome)
            setTabLayoutMediator(it.tbHome,it.vpHome)
        }

        initObserver()
    }

    private fun initObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.fabState.collect {
                        if(it){
                            showExtendedFab()
                        }else {
                            hideExtendedFab()
                        }
                    }
                }
            }
        }
    }

    private fun hideExtendedFab() {
        binding.btn2FloatingMain.visibility= View.INVISIBLE
        binding.btn3FloatingMain.visibility= View.INVISIBLE
        ObjectAnimator.ofFloat(binding.btn2FloatingMain, "translationY", 0f).apply{start()}
        ObjectAnimator.ofFloat(binding.btn3FloatingMain, "TranslationY", 0f).apply{start()}
    }

    private fun showExtendedFab() {
        binding.btn2FloatingMain.visibility= View.VISIBLE
        binding.btn3FloatingMain.visibility= View.VISIBLE
        ObjectAnimator.ofFloat(binding.btn2FloatingMain, "translationY", -150f).apply{start()}
        ObjectAnimator.ofFloat(binding.btn3FloatingMain, "TranslationY", -280f).apply{start()}
    }

    private fun getHomeTabs() : List<HomeTab> {
        // strings.xml에 정의한 home_tab string-array 를 가져오는 작업
        // 나중에 viewModel에 저장해서 관리하는 식으로 바꾸는게 좋을 듯?
        return resources.getStringArray(R.array.home_tab).map {
            HomeTab(
                title = it,
                selected = false
            )
        }
    }


    //viewPager 초기화 및 세팅하는 메서드
    private fun initViewPager(viewPager: ViewPager2) {
        homeTabAdapter = HomeTabAdapter(this)

        viewPager.adapter = homeTabAdapter
        viewPager.isUserInputEnabled = false

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setHomePosition(position)
            }
        })
    }

    private fun setTabLayoutMediator(tabLayout: TabLayout, viewPager: ViewPager2){
        TabLayoutMediator(tabLayout,viewPager){ tab, position ->
            getTabAtPosition(tab,position)
        }.attach()
    }


    private fun getTabAtPosition(tab : TabLayout.Tab, position : Int){
        tab.text = getHomeTabs()[position].title

        // 디자인 수정 코드 추가
    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(R.color.gray_100)
    }
}

