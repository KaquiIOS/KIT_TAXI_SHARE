/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.meongbyeol.taxishare.data.remote.apis.server

import com.meongbyeol.taxishare.data.model.MyTaxiShareItem
import com.meongbyeol.taxishare.data.model.TaxiShareInfoModel
import com.meongbyeol.taxishare.data.remote.apis.server.response.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TaxiShareInfoAPI {
    @POST("registerTaxiShareInfo")
    fun registerTaxiShareInfo(@Body serverRequest: Map<String, String>): Observable<TaxiShareRegisterResponse>

    @GET("loadTaxiShareInfo")
    fun getTaxiShareInfo(@Query("nextPageNum") nextPageNum: Int, @Query("uid") uid : String): Observable<MutableList<TaxiShareInfoModel>>

    @POST("participateTaxiShare")
    fun participateTaxiShare(@Body serverRequest: Map<String, String>) : Observable<ParticipateTaxiShareResponse>

    @POST("leaveTaxiShare")
    fun leaveTaxiShare(@Body serverRequest: Map<String, String>) : Observable<LeaveTaxiShareResponse>

    @POST("removeTaxiShare")
    fun removeTaxiShare(@Body serverRequest: Map<String, String>) : Observable<TaxiShareRemoveResponse>

    @POST("updateTaxiShare")
    fun updateTaxiShare(@Body serverRequest: Map<String, String>) : Observable<TaxiShareModifyResponse>

    @GET("loadDetailTaxiShareInfo")
    fun loadDetailTaxiShareInfo(@Query("postId") postId : String, @Query("uid") uid : String) : Observable<DetailTaxiShareLoadResponse>

    @GET("getMyTaxiShareList")
    fun loadMyTaxiShareList(@Query("stdId") stdId : String) : Observable<MutableList<MyTaxiShareItem>>
}