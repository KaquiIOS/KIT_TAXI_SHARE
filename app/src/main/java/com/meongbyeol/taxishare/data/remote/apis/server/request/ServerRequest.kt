/*
 * Created by WonJongSeong on 2019-04-10
 */

package com.meongbyeol.taxishare.data.remote.apis.server.request

interface ServerRequest {

    interface PostRequest {
        fun getRequest() : Map<String, String>
    }
    
    // 필요하면 GetRequest 만들기
}