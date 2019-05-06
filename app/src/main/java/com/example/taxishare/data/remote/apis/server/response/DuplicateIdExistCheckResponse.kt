/*
 * Created by WonJongSeong on 2019-04-09
 */

package com.example.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DuplicateIdExistCheckResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int
)