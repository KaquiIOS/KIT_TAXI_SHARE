/*
 * Created by WonJongSeong on 2019-08-05
 */

package com.example.taxishare.app

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class AlarmManagerImpl(
    private val alarmManager: AlarmManager,
    private val context: Context
) : AlarmManagerInterface {


    override fun setOneTimeAlarm(postId: Int, date: Calendar) {
        Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context, postId, it, 0)
        }.apply {
            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                date.timeInMillis,
                this
            )
            Log.d("Test", "알람이 $date 에 설정되었습니다")
        }
    }

    override fun setRepeatingAlarm() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelAlarm(postId: Int) {
        alarmManager.cancel(Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context, postId, it, 0)
        }).apply {
            Log.d("Test", "알람이 종료되었습니다")
        }
    }
}