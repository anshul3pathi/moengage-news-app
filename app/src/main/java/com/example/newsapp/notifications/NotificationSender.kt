package com.example.newsapp.notifications

interface NotificationSender {
    fun sendNotification(title: String, content: String)
}