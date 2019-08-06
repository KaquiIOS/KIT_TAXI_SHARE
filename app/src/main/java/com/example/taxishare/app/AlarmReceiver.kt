/*
 * Created by WonJongSeong on 2019-08-05
 */

package com.example.taxishare.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.taxishare.view.login.LoginActivity
import org.jetbrains.anko.toast


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "default")

            // 설정해둔 pending Intent를 이용해서 데이터를 여기다 보내주고
            // 시간, 언제, 어디서 출발하는지 정보 알려주기

            builder.setSmallIcon(com.example.taxishare.R.mipmap.ic_launcher_round)
            builder.setContentTitle("TaxiShare")
            builder.setContentText("알림 세숩 텍스트")

            // 액션 정의
            val intent2 = Intent(context, LoginActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent2,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            // 클릭 이벤트 설정
            builder.setContentIntent(pendingIntent)

            // 큰 아이콘 설정
            val largeIcon = BitmapFactory.decodeResource(context.resources, com.example.taxishare.R.mipmap.ic_launcher)
            builder.setLargeIcon(largeIcon)

            // 색상 변경
            builder.color = Color.RED

            val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
            // 오레오에서는 알림 채널을 매니저에 생성해야 한다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager!!.createNotificationChannel(
                    NotificationChannel(
                        "default",
                        "기본 채널",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
            }

            // 알림 통지
            manager!!.notify(1, builder.build())
        }
    }
}