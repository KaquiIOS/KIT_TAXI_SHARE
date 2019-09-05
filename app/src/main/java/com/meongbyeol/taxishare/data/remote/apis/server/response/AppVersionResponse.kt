/*
 * Created by WonJongSeong on 2019-09-05
 */

package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AppVersionResponse(
    @Expose
    @SerializedName("appVersion")
    val appVersion: Int
)