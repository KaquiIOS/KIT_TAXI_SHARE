/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.meongbyeol.taxishare.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.data.local.room.converter.RoomTypeConverter
import com.meongbyeol.taxishare.data.local.room.dao.LocationDAO
import com.meongbyeol.taxishare.data.local.room.dao.MyLocationDAO
import com.meongbyeol.taxishare.data.local.room.entity.LocationModel
import com.meongbyeol.taxishare.data.local.room.entity.MyLocationModel

@Database(entities = [LocationModel::class, MyLocationModel::class], version = 2)
@TypeConverters(value = [RoomTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDAO
    abstract fun myLocationDao() : MyLocationDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, Constant.ROOM_DB_NAME
            )
                .build()
    }
}