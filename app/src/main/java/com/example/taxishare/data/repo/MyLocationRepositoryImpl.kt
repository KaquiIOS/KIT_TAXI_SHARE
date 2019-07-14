/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.example.taxishare.data.repo

import com.example.taxishare.data.local.room.AppDatabase
import com.example.taxishare.data.local.room.entity.MyLocationModel
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.MyLocation
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MyLocationRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val typeMapper: TypeMapper
) : MyLocationRepository {

    companion object {
        @Volatile
        private var INSTANCE: MyLocationRepositoryImpl? = null

        fun getInstance(appDatabase: AppDatabase, typeMapper: TypeMapper): MyLocationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MyLocationRepositoryImpl(appDatabase, typeMapper)
            }
    }

    override fun gets(lastItemTime: Date): Observable<MutableList<MyLocation>> =
        appDatabase.myLocationDao().gets(lastItemTime)
            .map { typeMapper.myLocationModelToMyLocation(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun insert(selectedLocation: MyLocationModel): Completable =
        appDatabase.myLocationDao().insert(selectedLocation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun searchMyLocationByName(name: String): Observable<Boolean> =
        appDatabase.myLocationDao().searchMyLocationByName(name)
            .map { name == it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun delete(name: String): Completable =
        appDatabase.myLocationDao().delete(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}