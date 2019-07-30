/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.example.taxishare.data.remote.apis.server

import com.example.taxishare.data.model.Location
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceSearchAPI {


    @GET("getSearchPlacesInfo")
    fun getSearchPlacesInfo(@Query("query") query: String): Observable<MutableList<Location>>

}