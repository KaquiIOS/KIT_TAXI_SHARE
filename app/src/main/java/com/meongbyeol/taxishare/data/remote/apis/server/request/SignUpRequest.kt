/*
 * Created by WonJongSeong on 2019-04-11
 */

package com.meongbyeol.taxishare.data.remote.apis.server.request

class SignUpRequest constructor(
    private val id: String,
    private val pw: String,
    private val nickname: String,
    private val major: String,
    private val token : String
) : ServerRequest.PostRequest {

    companion object {
        private const val ID: String = "ID"
        private const val PW: String = "PW"
        private const val NICKNAME: String = "NICKNAME"
        private const val MAJOR: String = "MAJOR"
        private const val TOKEN : String = "token"
    }

    override fun getRequest(): Map<String, String> {
        val request: MutableMap<String, String> = HashMap()
        request[ID] = id
        request[PW] = pw
        request[NICKNAME] = nickname
        request[MAJOR] = major
        request[TOKEN] = token
        return request
    }
}