/*
 * Created by WonJongSeong on 2019-04-11
 */

package com.example.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequestResponse(
    @Expose
    @SerializedName("result")
    val isLoginSuccess : Boolean
)