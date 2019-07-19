/*
 * Created by WonJongSeong on 2019-07-15
 */

package com.example.taxishare.data.remote.apis.server.request

import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import java.util.*

class RegisterTaxiShareRequest(
    private val title: String,
    private val startDate: Date,
    private val passengerNum: Int,
    private val startLocation: Location,
    private val endLocation: Location,
    private val registerDate: Date
) : ServerRequest.PostRequest {

    companion object {
        private const val UID  = "userId"
        private const val TITLE = "title"
        private const val START_DATE = "startDate"
        private const val REGISTER_DATE = "registerDate"
        private const val NUM_OF_PASSENGER = "numOfPassenger"
        private const val START_LOCATION = "startLocation"
        private const val END_LOCATION = "endLocation"
    }

    override fun getRequest(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        params[UID] = Constant.USER_ID
        params[TITLE] = title
        params[START_DATE] = startDate.time.toString()
        params[REGISTER_DATE] = registerDate.time.toString()
        params[NUM_OF_PASSENGER] = passengerNum.toString()
        params[START_LOCATION] = startLocation.toString()
        params[END_LOCATION] = endLocation.toString()

        return params
    }
}