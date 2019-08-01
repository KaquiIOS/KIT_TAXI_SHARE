/*
 * Created by WonJongSeong on 2019-08-01
 */

package com.example.taxishare.data.remote.apis.server.request

import com.example.taxishare.data.model.Location
import java.util.*
import kotlin.collections.HashMap

data class TaxiShareModifyRequest(private val postId: String,
                                  private val title : String,
                                  private val startDate : Date,
                                  private val startLocation : Location,
                                  private val endLocation : Location,
                                  private val maxNum : Int) : ServerRequest.PostRequest {
    companion object {
        private const val POST_ID: String = "id"
        private const val TITLE = "title"
        private const val START_DATE = "startDate"
        private const val START_LOCATION = "startLocation"
        private const val END_LOCATION = "endLocation"
        private const val MAX_NUM = "maxNum"
    }

    override fun getRequest(): Map<String, String> {
        val request: MutableMap<String, String> = HashMap()
        request[POST_ID] = postId
        request[TITLE] = title
        request[START_DATE] = startDate.time.toString()
        request[START_LOCATION] = startLocation.toString()
        request[END_LOCATION] = endLocation.toString()
        request[MAX_NUM] = maxNum.toString()

        return request
    }
}
