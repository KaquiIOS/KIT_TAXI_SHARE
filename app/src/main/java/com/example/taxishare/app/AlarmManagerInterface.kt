/*
 * Created by WonJongSeong on 2019-08-05
 */

package com.example.taxishare.app

import java.util.*

interface AlarmManagerInterface {

    fun setOneTimeAlarm(postId : Int, date : Calendar)
    fun setRepeatingAlarm()
    fun cancelAlarm(postId: Int)

}