/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.meongbyeol.taxishare.data.repo

import com.meongbyeol.taxishare.data.local.room.entity.MyLocationModel
import com.meongbyeol.taxishare.data.model.MyLocation
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

interface MyLocationRepository {

    fun gets(lastItemTime : Date) : Observable<MutableList<MyLocation>>
    fun insert(selectedLocation : MyLocationModel) : Completable
    fun searchMyLocationByName(name : String) : Observable<Boolean>
    fun delete(name : String) : Completable
}