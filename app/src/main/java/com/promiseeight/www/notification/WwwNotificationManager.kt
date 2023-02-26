package com.promiseeight.www.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.promiseeight.www.R

object WwwNotificationManager {
    var index = 1
    fun createNotification(context: Context, wwwNotificationData : WwwNotificationModel) {
        val notificationManager : NotificationManager
                = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(context,notificationManager)

        notificationManager.notify(
            index, buildNotification(
                context,wwwNotificationData
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
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(
        context : Context,
        wwwNotificationData : WwwNotificationModel
    ) : Notification {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(getDestinationIdFromContentType(wwwNotificationData.contentType))
            .setArguments(getArgument(wwwNotificationData))
            .createPendingIntent()

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setContentTitle(wwwNotificationData.title)
            .setContentText(wwwNotificationData.text)
            .setSmallIcon(R.drawable.ic_app_logo_round)
            .setAutoCancel(true)
            .build()
    }

    private fun getDestinationIdFromContentType(contentType : ContentType) : Int {
        return when(contentType){
            ContentType.MEETING -> R.id.fragment_meeting_detail
            else -> R.id.fragment_meeting_detail
        }
    }

    private fun getArgument(wwwNotificationData: WwwNotificationModel) : Bundle {
        return when(wwwNotificationData.contentType){
            ContentType.MEETING -> Bundle().apply {
                putString("meetingId",wwwNotificationData.contentId.toString())
            }
            else -> Bundle().apply {
                putString("meetingId",wwwNotificationData.contentId.toString())
            }
        }
    }

    private const val CHANNEL_ID = "www_notification_push_channel"
}