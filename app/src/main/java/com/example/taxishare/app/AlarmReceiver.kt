/*
 * Created by WonJongSeong on 2019-08-05
 */

package com.example.taxishare.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.jetbrains.anko.toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.BOOT_COMPLIED") {
            context?.toast("Toast Message")
            Log.d("Test", "AlarmTest")
        }
    }
}