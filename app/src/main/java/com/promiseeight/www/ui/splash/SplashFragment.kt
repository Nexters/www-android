package com.promiseeight.www.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.promiseeight.www.databinding.FragmentSplashBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Login 화면이 없기 때문에 Splash에서 AccessToken을 받고 넘어가도록 하였음.
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel : SplashViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setFirebaseMessagingTokenSuccessListener()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isAccessTokenInDevice.collectLatest { isInDevice ->
                        if(isInDevice.isSuccess){
                            findNavController().navigate(SplashFragmentDirections.actionFragmentSplashToFragmentHome())
                        }
                    }
                }

                launch {
                    viewModel.splashStatus.collectLatest { status ->
                        when(status){
                            SplashStatus.ERROR -> {
                                //TODO
                            } else -> {
                                //TODO
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setFirebaseMessagingTokenSuccessListener() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            viewModel.getAccessTokenWithFcmToken(fcmToken = it)
        }
    }

}