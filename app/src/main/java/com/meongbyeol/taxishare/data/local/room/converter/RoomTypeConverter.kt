/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.meongbyeol.taxishare.data.local.room.converter

import androidx.room.TypeConverter
import java.util.*

class RoomTypeConverter {

    companion object{
        @TypeConverter
        @JvmStatic
        fun dateToLong(date : Date) = date.time

        @TypeConverter
        @JvmStatic
        fun longToDate(date : Long) = Date(date)
    }
}