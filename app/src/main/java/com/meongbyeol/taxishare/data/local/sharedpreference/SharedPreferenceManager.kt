/*
 * Created by WonJongSeong on 2019-03-27
 */

package com.meongbyeol.taxishare.data.local.sharedpreference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferenceManager {

    // Constructor
    private fun SharedPreferenceManager() {}

    companion object {

        private const val TEAM_H = "TEAM_H"
        private const val PREF_PASSWORD = "PREF_PASSWORD"
        private const val PREF_ID = "PREF_ID"
        private const val PREF_IS_AUTO_LOGIN = "PREF_IS_AUTO_LOGIN"

        // fields
        private var preferences: SharedPreferences? = null

        // getInstance()
        fun getInstance(context: Context): SharedPreferenceManager {
            if (preferences == null) {
                preferences = context.getSharedPreferences(TEAM_H, MODE_PRIVATE)
            }
            return LazyHolder.INSTANCE
        }
    }

    fun setPreferenceId(id: String) {
        val editor = preferences!!.edit()
        editor.putString(PREF_ID, id)
        editor.apply()
    }

    fun getPreferenceId(defaultPassword: String): String? {
        return preferences!!.getString(PREF_ID, defaultPassword)
    }

    fun setPreferencePassword(password: String) {
        val editor = preferences!!.edit()
        editor.putString(PREF_PASSWORD, password)
        editor.apply()
    }

    fun getPreferencePassword(defaultPassword: String): String? {
        return preferences!!.getString(PREF_PASSWORD, defaultPassword)
    }

    fun setPreferenceIsAutoLogin(isAutoLogin: Boolean) {
        val editor = preferences!!.edit()
        editor.putBoolean(PREF_IS_AUTO_LOGIN, isAutoLogin)
        editor.apply()
    }

    fun getPreferenceIsAutoLogin(): Boolean {
        return preferences!!.getBoolean(PREF_IS_AUTO_LOGIN, false)
    }

    // LazyHolder 클래스 - 싱글톤
    private object LazyHolder {
        val INSTANCE = SharedPreferenceManager()
    }

    fun getPreferences(): SharedPreferences? {
        return preferences
    }
}