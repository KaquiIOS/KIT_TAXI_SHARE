/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.repo

import com.example.taxishare.data.local.room.AppDatabase
import com.example.taxishare.data.local.room.entity.LocationModel
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.Location
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class LocationRepositoryImpl private constructor(
    private val appDatabase: AppDatabase,
    private val typeMapper: TypeMapper
) : LocationRepository {

    companion object {
        @Volatile
        private var INSTANCE: LocationRepository? = null

        fun getInstance(appDatabase: AppDatabase, typeMapper: TypeMapper): LocationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationRepositoryImpl(appDatabase, typeMapper)
            }
    }

    override fun insertLocation(location: LocationModel): Completable =
        appDatabase.locationDao().insertLocation(location)
            .subscribeOn(Schedulers.io())
            .doOnComplete { } // to do something when complete

    override fun getLocations(lastItemTime: Date): Observable<List<Location>> =
        appDatabase.locationDao().getLocation(lastItemTime)
            .map { typeMapper.locationModelToLocation(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}