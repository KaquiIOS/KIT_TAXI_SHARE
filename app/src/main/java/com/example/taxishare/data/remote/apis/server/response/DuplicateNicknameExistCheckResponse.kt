/*
 * Created by WonJongSeong on 2019-05-01
 */

package com.example.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DuplicateNicknameExistCheckResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode : Int
)