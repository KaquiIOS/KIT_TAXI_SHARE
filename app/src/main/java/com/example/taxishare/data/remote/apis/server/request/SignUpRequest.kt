/*
 * Created by WonJongSeong on 2019-04-11
 */

package com.example.taxishare.data.remote.apis.server.request

class SignUpRequest constructor(
    private val id: String,
    private val pw: String,
    private val nickname: String,
    private val major: String
) : ServerRequest.PostRequest {

    companion object {
        private const val ID: String = "ID"
        private const val PW: String = "PW"
        private const val NICKNAME: String = "NICKNAME"
        private const val MAJOR: String = "MAJOR"
    }

    override fun getRequest(): Map<String, String> {
        val request: MutableMap<String, String> = HashMap()
        request[ID] = id
        request[PW] = pw
        request[NICKNAME] = nickname
        request[MAJOR] = major
        return request
    }
}