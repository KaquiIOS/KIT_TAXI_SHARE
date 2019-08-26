package com.meongbyeol.taxishare.data.remote.apis.server.request

data class FindPasswordRequest(private val stdId: String) : ServerRequest.PostRequest {
    companion object {
        private const val STD_ID: String = "stdId"
    }

    override fun getRequest(): Map<String, String> {
        val request: MutableMap<String, String> = HashMap()
        request[STD_ID] = stdId

        return request
    }
}