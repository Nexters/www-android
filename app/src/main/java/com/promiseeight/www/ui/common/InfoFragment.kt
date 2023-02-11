package com.promiseeight.www.ui.common

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.promiseeight.www.R

abstract class InfoFragment<B : ViewDataBinding> : BaseFragment<B>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun initView() {

    }

    fun setParentFragmentBranch(
        onJoin: () -> Unit,
        onAdd: () -> Unit
    ) {
        when (this.parentFragment) {
            is JoinNavHostFragment -> {
                onJoin()
            }
            is AddNavHostFragment -> {
                onAdd()
            }
        }
    }

    fun getHostFragment() : Fragment {
        return requireParentFragment().requireParentFragment()
    }

    companion object {
        const val ACTION_ADD_NAME_TO_USER_NAME =
            R.id.action_fragment_add_meeting_info_name_to_fragment_add_meeting_info_user_name
        const val ACTION_ADD_USER_NAME_TO_CAPACITY =
            R.id.action_fragment_add_meeting_info_user_name_to_fragment_add_meeting_info_capacity
        const val ACTION_ADD_CAPACITY_TO_PERIOD =
            R.id.action_fragment_add_meeting_info_capacity_to_fragment_add_meeting_info_period
        const val ACTION_ADD_PERIOD_TO_DATE =
            R.id.action_fragment_add_meeting_info_period_to_fragment_add_meeting_info_date
        const val ACTION_ADD_DATE_TO_PLACE =
            R.id.action_fragment_add_meeting_info_date_to_fragment_add_meeting_info_place

        const val ACTION_JOIN_CODE_TO_USER_NAME =
            R.id.action_fragment_join_meeting_info_code_to_fragment_join_meeting_info_user_name
        const val ACTION_JOIN_USER_NAME_TO_DATE =
            R.id.action_fragment_join_meeting_info_user_name_to_fragment_join_meeting_info_date
        const val ACTION_JOIN_DATE_TO_PLACE =
            R.id.action_fragment_join_meeting_info_date_to_fragment_join_meeting_info_place
    }
}