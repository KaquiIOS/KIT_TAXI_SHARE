/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.mapper


import com.example.taxishare.data.local.room.entity.LocationModel
import com.example.taxishare.data.local.room.entity.MyLocationModel
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.MyLocation

object TypeMapper {

    fun locationModelToLocation(locationModelList: List<LocationModel>): MutableList<Location> {
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

    fun myLocationModelToMyLocation(myLocationList: MutableList<MyLocationModel>): MutableList<MyLocation> {
        val convertedList: MutableList<MyLocation> = mutableListOf()

        myLocationList.forEach {
            convertedList.add(
                MyLocation(
                    it.saveName,
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