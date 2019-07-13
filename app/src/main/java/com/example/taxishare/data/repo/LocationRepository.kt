/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.repo

import com.example.taxishare.data.local.room.entity.LocationModel
import com.example.taxishare.data.model.Location
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

interface LocationRepository {
    fun insertLocation(location: LocationModel): Completable
    fun getLocations(lastItemTime: Date): Observable<MutableList<Location>>
}