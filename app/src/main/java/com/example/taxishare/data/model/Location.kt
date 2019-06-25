/*
 * Created by WonJongSeong on 2019-05-14
 */

package com.example.taxishare.data.model

data class Location(val latitude : Double, val longitude : Double,
                    val locationName : String, val locationAddress : String) {
    override fun toString(): String {
        // FIXME : toString 변경하기
        return super.toString()
    }
}