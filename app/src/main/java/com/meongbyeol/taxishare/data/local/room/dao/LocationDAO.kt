/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.meongbyeol.taxishare.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meongbyeol.taxishare.data.local.room.entity.LocationModel
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

@Dao
interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: LocationModel): Completable

    @Query("SELECT * FROM location where registerTime < :lastItemTime ORDER BY registerTime LIMIT 10 ")
    fun getLocation(lastItemTime: Date): Observable<List<LocationModel>>

}