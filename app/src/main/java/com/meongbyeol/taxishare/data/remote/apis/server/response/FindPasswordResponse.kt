package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FindPasswordResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int
)