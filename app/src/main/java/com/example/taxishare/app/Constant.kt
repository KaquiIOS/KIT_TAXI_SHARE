package com.example.taxishare.app

class Constant {
    companion object {
        const val TIME_LIMIT : Long = 2L
        const val ALL_SIGN_UP_REQUEST_CHECKED = 63
        const val BASE_URL : String = "http://202.31.202.207:3000"


        const val GALLERY_REQUEST_CODE : Int = 3001
        const val CAMERA_REQUEST_CODE : Int = 3002

        fun toInt(boolean: Boolean, int: Int) : Int = if (boolean) int else 0
    }
}