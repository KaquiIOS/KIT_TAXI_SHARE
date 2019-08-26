/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.meongbyeol.taxishare.data.remote.apis.server

import com.meongbyeol.taxishare.data.remote.apis.server.response.LoginRequestResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {

    @POST("login")
    fun loginRequest(@Body serverRequest: Map<String, String>): Observable<LoginRequestResponse>

}