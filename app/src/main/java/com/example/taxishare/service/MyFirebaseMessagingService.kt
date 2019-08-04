/*
 * Created by WonJongSeong on 2019-08-04
 */

package com.example.taxishare.service

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.taxishare.view.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Exception

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        val TAG: String = MyFirebaseMessagingService::class.java.simpleName
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        // when not null
        remoteMessage?.from.apply {
            Log.d("Test", remoteMessage.toString())
            sendToActivity(
                applicationContext,
                remoteMessage?.from ?: "",
                remoteMessage?.notification?.title ?: "",
                remoteMessage?.notification?.body ?: "",
                remoteMessage?.data.toString()
            )
        }
    }

    override fun onSendError(p0: String?, p1: Exception?) {
        super.onSendError(p0, p1)
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
    }

    private fun sendToActivity(context: Context, from: String, title: String, body: String, contents: String) {

        Intent(context, MainActivity::class.java).apply {
//            putExtra("from", from)
//            putExtra("title", title)
//            putExtra("body", body)
//            putExtra("contents", contents)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(this)
        }
    }
}