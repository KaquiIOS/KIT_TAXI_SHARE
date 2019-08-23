package com.example.taxishare.app

import com.example.taxishare.data.model.User
import java.text.SimpleDateFormat
import java.util.*

class Constant {
    companion object {

        lateinit var CURRENT_USER: User
        lateinit var USER_ID: String

        const val TIME_LIMIT: Long = 2L
        const val ALL_SIGN_UP_REQUEST_CHECKED = 63
        const val BASE_URL: String = "http://kittaxishare.cafe24app.com/"
        const val REGISTER_TAXI_TITLE_MIN_LENGTH = 300

        // Date Foramt
        const val DATE_FORMAT: String = "yyyy-MM-dd hh:mm"

        val DATE_FORMATTER: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
        val LOCATION_SEARCH_HINT: String = "hint"

        const val SEARCH_HISTORY_MAP_HEIGHT = 600

        const val GALLERY_REQUEST_CODE: Int = 3001
        const val CAMERA_REQUEST_CODE: Int = 3002

        const val REGISTER_START_LOCATION_CODE: Int = 4001
        const val REGISTER_END_LOCATION_CODE: Int = 4002

        const val REGISTER_TAXI_SHARE: Int = 5001
        const val REGISTER_TAXI_SHARE_STR: String = "taxiShareInfoRegister"

        const val MODIFY_TAXI_SHARE: Int = 6002
        const val MODIFY_TAXI_SHARE_STR: String = "taxiShareInfoModify"

        const val TAXISHARE_DETAIL: Int = 7001
        const val TAXISHARE_DETAIL_STR: String = "taxiShareDetail"

        const val DATA_REMOVED: Int = 10

        // Debounce Time
        const val EDIT_TEXT_DEBOUNCE_TIME: Long = 500

        // AppDatabase Name
        const val ROOM_DB_NAME = "db.db"

        // Map Zoom degree
        const val MAP_ZOOM_IN = 15.0f

        // POST ID
        const val POST_ID = "postId"
        const val ALARM_NOTIFY_TIME: Long = 1800000

        const val START_LOCATION_SEARCH_CODE: Int = 8001
        const val END_LOCATION_SEARCH_CODE: Int = 9001

        const val LOCATION_SAVE_STR: String = "location"

        const val ALARM_MESSAGE_STR : String = "Alarm"

        const val LEAVE_TAXI_PARTY : Int = 11
    }
}