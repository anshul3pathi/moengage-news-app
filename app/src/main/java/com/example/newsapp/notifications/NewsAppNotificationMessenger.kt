package com.example.newsapp.notifications

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class NewsAppNotificationMessenger : FirebaseMessagingService() {
    private val notificationSender = DefaultNotificationSender(applicationContext)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Check if message contains a notification payload.
        remoteMessage.data.let { notificationPayload ->
            val title = notificationPayload["title"] ?: return@let
            val body = notificationPayload["body"] ?: return@let
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, body: String) {
        notificationSender.sendNotification(title, body)
    }
}