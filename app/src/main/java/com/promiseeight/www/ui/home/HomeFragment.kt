package com.promiseeight.www.ui.home

import android.animation.ObjectAnimator
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentHomeBinding
import com.promiseeight.www.ui.adapter.HomeTabAdapter
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var isFabOpen = false //floating

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
            btnVisible()
            toggleFab()
        }

        binding.btn3FloatingMain.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentAddMeeting()
            )
        }

        binding.btn2FloatingMain.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentJoinMeeting()
            )
        }

        binding.ivSetting.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentSetting()
            )
        }


        binding.let{
            initViewPager(it.vpHome)
            setTabLayoutMediator(it.tbHome,it.vpHome)
        }
    }

    private fun toggleFab() {
        //????????? ?????? ??????
        if(isFabOpen) {
            ObjectAnimator.ofFloat(binding.btn2FloatingMain, "translationY", 0f).apply{start()}
            ObjectAnimator.ofFloat(binding.btn3FloatingMain, "TranslationY", 0f).apply{start()}
            binding.btn1FloatingMain.setImageResource(R.drawable.ic_floating_vector_default)
        } else {
            ObjectAnimator.ofFloat(binding.btn2FloatingMain, "translationY", -150f).apply{start()}
            ObjectAnimator.ofFloat(binding.btn3FloatingMain, "TranslationY", -310f).apply{start()}
            binding.btn1FloatingMain.setImageResource(R.drawable.ic_floating_vector_click)
        }

        isFabOpen = !isFabOpen

    }


    private fun btnVisible() {
        if(isFabOpen) {
            binding.btn2FloatingMain.visibility= View.INVISIBLE;
            binding.btn3FloatingMain.visibility= View.INVISIBLE;

        } else {
            binding.btn2FloatingMain.visibility= View.VISIBLE;
            binding.btn3FloatingMain.visibility= View.VISIBLE;
        }
    }

    private fun getHomeTabs() : List<HomeTab> {
        // strings.xml??? ????????? home_tab string-array ??? ???????????? ??????
        // ????????? viewModel??? ???????????? ???????????? ????????? ???????????? ?????? ????
        return resources.getStringArray(R.array.home_tab).map {
            HomeTab(
                title = it,
                selected = false
            )
        }
    }


    //viewPager ????????? ??? ???????????? ?????????
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

        // ????????? ?????? ?????? ??????
    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(R.color.gray_100)
    }
}

