/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.example.taxishare.data.repo

import com.example.taxishare.data.local.room.entity.MyLocationModel
import com.example.taxishare.data.model.MyLocation
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

interface MyLocationRepository {

    fun gets(lastItemTime : Date) : Observable<MutableList<MyLocation>>
    fun insert(selectedLocation : MyLocationModel) : Completable
    fun searchMyLocationByName(name : String) : Observable<Boolean>
}