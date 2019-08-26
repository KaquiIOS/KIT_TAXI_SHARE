/*
 * Created by WonJongSeong on 2019-08-05
 */

package com.meongbyeol.taxishare.app

import java.util.*

interface AlarmManagerInterface {

    fun setOneTimeAlarm(
        postId: Int, date: Calendar, startLocation: String,
        endLocation: String
    )

    fun setRepeatingAlarm()
    fun cancelAlarm(postId: Int)

}