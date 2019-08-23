/*
 * Created by WonJongSeong on 2019-08-05
 */

package com.example.taxishare.app

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.taxishare.data.mapper.TypeMapper
import java.util.*

class AlarmManagerImpl(
    private val alarmManager: AlarmManager,
    private val context: Context
) : AlarmManagerInterface {


    override fun setOneTimeAlarm(
        postId: Int, date: Calendar, startLocation: String,
        endLocation: String
    ) {
        Intent(context, AlarmReceiver::class.java).let {
            it.putExtra(
                Constant.ALARM_MESSAGE_STR, String.format(
                    "%s 시 합승 출발 알림입니다.\n출발지 : %s\n도착지: %s",
                    TypeMapper.dateToString(date.time), startLocation, endLocation
                )
            )
            PendingIntent.getBroadcast(context, postId, it, 0)
        }.apply {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                date.timeInMillis - (1800000),
                this
            )
            Log.d("Test", "알람이 $date 에 설정되었습니다")
        }
    }

    override fun setRepeatingAlarm() {

    }

    override fun cancelAlarm(postId: Int) {
        alarmManager.cancel(Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context, postId, it, 0)
        }).apply {
            Log.d("Test", "알람이 종료되었습니다")
        }
    }
}