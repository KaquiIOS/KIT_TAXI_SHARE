/*
 * Created by WonJongSeong on 2019-04-09
 */

package com.example.taxishare.data.remote.apis.server

import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.response.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerAPI {

    @POST("same_id_check")
    fun checkSameIdExist(@Body serverRequest : Map<String, String>): Observable<DuplicateIdExistCheckResponse>

    @POST("same_nickname_check")
    fun checkSameNickNameExist(@Body serverRequest: Map<String, String>) : Observable<DuplicateNicknameExistCheckResponse>

    @POST("login")
    fun loginRequest(@Body serverRequest: Map<String, String>) : Observable<LoginRequestResponse>

    @POST("sign_up_request")
    fun signUpRequest(@Body serverRequest: Map<String, String>) : Observable<SignUpRequestResponse>

    @GET("getSearchPlacesInfo")
    fun getSearchPlacesInfo(@Query("query") query : String) : Observable<MutableList<Location>>

    @GET("loadTaxiShareInfo")
    fun getTaxiShareInfo(@Query("nextPageNum") nextPageNum : Int) : Observable<MutableList<TaxiShareInfo>>

    @POST("registerTaxiShareInfo")
    fun registerTaxiShareInfo(@Body serverRequest: Map<String, String>) : Observable<TaxiShareRegisterResponse>
}