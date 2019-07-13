/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.mapper


import com.example.taxishare.data.local.room.entity.LocationModel
import com.example.taxishare.data.model.Location

object TypeMapper {

    fun locationModelToLocation(locationModelList: List<LocationModel>): List<Location> {
        val convertedList: MutableList<Location> = mutableListOf()

        locationModelList.forEach {
            convertedList.add(
                Location(
                    it.latitude,
                    it.longitude,
                    it.locationName,
                    it.roadAddress,
                    it.jibunAddress
                )
            )
        }

        return convertedList
    }
}