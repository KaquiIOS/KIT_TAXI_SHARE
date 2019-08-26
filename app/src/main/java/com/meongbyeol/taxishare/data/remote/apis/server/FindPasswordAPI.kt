package com.meongbyeol.taxishare.data.remote.apis.server

import com.meongbyeol.taxishare.data.remote.apis.server.response.FindPasswordResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface FindPasswordAPI {
    @POST("findPassword")
    fun sendTemporaryPassword(@Body serverRequest : Map<String, String>) : Observable<FindPasswordResponse>
}