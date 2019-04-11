/*
 * Created by WonJongSeong on 2019-04-09
 */

package com.example.taxishare.data.remote.apis.server

import com.example.taxishare.data.remote.apis.server.response.DuplicateIdCheckResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ServerAPI {

    @GET("login")
    fun checkSameIdExist(): Observable<DuplicateIdCheckResponse>


}