/*
 * Created by WonJongSeong on 2019-04-11
 */

package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequestResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("nickname")
    val nickname: String,

    @Expose
    @SerializedName("major")
    val major: String
)