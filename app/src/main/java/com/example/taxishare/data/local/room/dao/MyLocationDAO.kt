/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.example.taxishare.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taxishare.data.local.room.entity.MyLocationModel
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

@Dao
interface MyLocationDAO {

    @Query("SELECT * FROM mylocation where registerTime < :lastItemTime ORDER BY registerTime LIMIT 10 ")
    fun gets(lastItemTime : Date) : Observable<MutableList<MyLocationModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(selectedLocation : MyLocationModel) : Completable

    @Query("SELECT name FROM mylocation where name == :name LIMIT 1")
    fun searchMyLocationByName(name : String) : Observable<String>
}