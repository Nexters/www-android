package com.promiseeight.www.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.promiseeight.www.notification.WwwNotificationManager.createNotification
import com.promiseeight.www.notification.WwwNotificationModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.json.JSONObject
import timber.log.Timber

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
            remoteMessage.data.get("data")?.let {
                try {
                    val data = Gson().fromJson(it,WwwNotificationModel::class.java)
                    createNotification(context = this,data)
                    Timber.d("asdasd ${data.contentType}")
                } catch (e : Exception){
                    Timber.d("WwwException : onMessageReceived ")
                }

            }

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