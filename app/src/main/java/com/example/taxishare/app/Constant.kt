package com.example.taxishare.app

class Constant {
    companion object {
        const val TIME_LIMIT : Long = 2L
        const val ALL_SIGN_UP_REQUEST_CHECKED = 63


        fun toInt(boolean: Boolean, int: Int) : Int = if (boolean) int else 0

    }
}