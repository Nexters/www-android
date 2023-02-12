package com.promiseeight.www.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@AndroidEntryPoint
class WwwFirebaseMessagingService: FirebaseMessagingService()  {
    private val supervisorJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + supervisorJob)

    // 새 토큰이 생성될 때마다 실행됨
    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }


    // Push 메세지 받으면 실행
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FirebaseToken", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FirebaseToken", "Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("FirebaseToken", "Message Notification Body: ${it.body}")
        }
    }

    override fun onDestroy() {
        supervisorJob.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "FirebaseToken"
    }


}