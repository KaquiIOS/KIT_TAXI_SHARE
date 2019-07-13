/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

@Entity(tableName = "location", primaryKeys = ["latitude", "longitude"])
data class LocationModel(
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "locationName")
    val locationName: String,
    @ColumnInfo(name = "roadAddress")
    val roadAddress: String,
    @ColumnInfo(name = "jibunAddress")
    val jibunAddress: String,
    @ColumnInfo(name = "registerTime")
    val registerTime: Date
)