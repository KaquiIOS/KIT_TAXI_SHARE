/*
 * Created by WonJongSeong on 2019-07-05
 */

package com.example.taxishare.data.remote.apis.server.response

import com.example.taxishare.data.model.Location
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchPlacesResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int,
    @Expose
    @SerializedName("searchList")
    val searchList : MutableList<Location>
) {

}