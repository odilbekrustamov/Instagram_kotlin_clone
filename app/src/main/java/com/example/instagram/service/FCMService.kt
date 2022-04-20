package com.example.instagram.service

import com.example.instagram.utils.Logger
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    val TAG = FCMService::class.java.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logger.i(TAG, "Refreshed token :: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO : send token to tour server
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Logger.i(TAG, "Message :: $message")
    }
}