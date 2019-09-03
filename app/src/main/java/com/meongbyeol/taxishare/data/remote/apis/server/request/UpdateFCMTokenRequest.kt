/*
 * Created by WonJongSeong on 2019-09-03
 */

package com.meongbyeol.taxishare.data.remote.apis.server.request

data class UpdateFCMTokenRequest(
    private val userId : String,
    private val token : String
)  : ServerRequest.PostRequest {

    companion object {
        private const val USER_ID: String = "id"
        private const val TOKEN = "token"
    }

    override fun getRequest(): Map<String, String> {
        val request: MutableMap<String, String> = HashMap()
        request[USER_ID] = userId
        request[TOKEN] = token
        return request
    }
}