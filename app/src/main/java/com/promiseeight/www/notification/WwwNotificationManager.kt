package com.promiseeight.www.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavDeepLinkBuilder
import com.promiseeight.www.R

object WwwNotificationManager {
    var index = 1
    fun createNotification(context: Context) {
        val notificationManager : NotificationManager
            = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(context,notificationManager)

        notificationManager.notify(
            index++, buildNotification(
                context,R.id.fragment_home
            )
        )
    }

    private fun createNotificationChannel(context: Context, notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.www_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.www_notification_channel_description)
            }
        }
    }

    private fun buildNotification(
        context : Context,
        destinationId : Int,
        argument : Bundle? = null
    ) : Notification {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(destinationId)
            .setArguments(argument)
            .createPendingIntent()

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setContentTitle("WWW")
            .setContentText("WWW 알림 테스트입니다.")
            .build()
    }

    private const val CHANNEL_ID = "www_notification_push_channel"
}