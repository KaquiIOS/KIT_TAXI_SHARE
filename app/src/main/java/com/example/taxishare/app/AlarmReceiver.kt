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
import androidx.core.app.NotificationCompat
import com.example.taxishare.R
import com.example.taxishare.view.login.LoginActivity


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "default")

            // 설정해둔 pending Intent를 이용해서 데이터를 여기다 보내주고
            // 시간, 언제, 어디서 출발하는지 정보 알려주기

            builder.setSmallIcon(R.drawable.ic_taxi_stop)
            builder.setContentTitle("TaxiShare")

            if(intent != null) {
                val message = intent.getStringExtra(Constant.ALARM_MESSAGE_STR)
                builder.setStyle(NotificationCompat.BigTextStyle().bigText(message))
            } else {
                builder.setContentText("합승 추가 정보를 읽어오는데 실패했습니다")
            }

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
            val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_taxi_stop)
            builder.setLargeIcon(largeIcon)

            // 색상 변경
            builder.color = Color.BLACK

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