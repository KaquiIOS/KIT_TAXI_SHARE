/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.meongbyeol.taxishare.data.remote.apis.server

import com.meongbyeol.taxishare.data.remote.apis.server.response.LoginRequestResponse
import com.meongbyeol.taxishare.data.remote.apis.server.response.UpdateFCMTokenResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {

    @POST("login")
    fun loginRequest(@Body serverRequest: Map<String, String>): Observable<LoginRequestResponse>

    @POST("updateToken")
    fun updateTokenRequest(@Body serverRequest: Map<String, String>) : Observable<UpdateFCMTokenResponse>
}