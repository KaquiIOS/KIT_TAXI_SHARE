/*
 * Created by WonJongSeong on 2019-05-14
 */

package com.meongbyeol.taxishare.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location (
    @Expose
    @SerializedName("latitude")
    val latitude: Double,
    @Expose
    @SerializedName("longitude")
    val longitude: Double,
    @Expose
    @SerializedName("name")
    val locationName: String,
    @Expose
    @SerializedName("road_address")
    val roadAddress: String,
    @Expose
    @SerializedName("jibun_address")
    val jibunAddress: String
) : Serializable {
    override fun toString(): String {
        return "{\"latitude\" : $latitude," +
                "\"longitude\" : $longitude," +
                "\"name\" : \"$locationName\"," +
                "\"road_address\" : \"$roadAddress\"," +
                "\"jibun_address\" : \"$jibunAddress\"}"
    }

    companion object {
        val DIFF_UTIL: DiffUtil.ItemCallback<Location> = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean =
                (oldItem.latitude == newItem.latitude) && (oldItem.longitude == newItem.longitude)

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean =
                (oldItem.latitude == newItem.latitude) && (oldItem.longitude == newItem.longitude)
        }
    }
}