package com.example.taxishare.app

import java.text.SimpleDateFormat
import java.util.*

class Constant {
    companion object {
        const val TIME_LIMIT : Long = 2L
        const val ALL_SIGN_UP_REQUEST_CHECKED = 63
        const val BASE_URL : String = "http://kittaxishare.cafe24app.com/"
        const val REGISTER_TAXI_TITLE_MIN_LENGTH = 3

        private const val DATE_FORMAT : String = "yyyy-MM-dd hh:mm"


        val DATE_FORMATTER : SimpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
        val LOCATION_SEARCH_HINT : String = "hint"

        const val SEARCH_HISTORY_MAP_HEIGHT = 600

        const val GALLERY_REQUEST_CODE : Int = 3001
        const val CAMERA_REQUEST_CODE : Int = 3002

        const val REGISTER_START_LOCATION_CODE : Int = 4001
        const val REGISTER_END_LOCATION_CODE : Int = 4002


        // Debounce Time
        const val EDIT_TEXT_DEBOUNCE_TIME : Long = 500

        // AppDatabase Name
        const val ROOM_DB_NAME = "db.db"

    }
}