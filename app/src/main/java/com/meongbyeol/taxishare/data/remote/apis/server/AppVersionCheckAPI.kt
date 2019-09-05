/*
 * Created by WonJongSeong on 2019-09-05
 */

package com.meongbyeol.taxishare.data.remote.apis.server

import com.meongbyeol.taxishare.data.remote.apis.server.response.AppVersionResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface AppVersionCheckAPI {

    @GET("getAppVersion")
    fun getAppVersion() : Observable<AppVersionResponse>
}