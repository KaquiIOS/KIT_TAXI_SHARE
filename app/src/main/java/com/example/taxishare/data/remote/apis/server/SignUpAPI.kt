/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.example.taxishare.data.remote.apis.server

import com.example.taxishare.data.remote.apis.server.response.DuplicateIdExistCheckResponse
import com.example.taxishare.data.remote.apis.server.response.DuplicateNicknameExistCheckResponse
import com.example.taxishare.data.remote.apis.server.response.SignUpRequestResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpAPI {

    @POST("same_id_check")
    fun checkSameIdExist(@Body serverRequest: Map<String, String>): Observable<DuplicateIdExistCheckResponse>

    @POST("same_nickname_check")
    fun checkSameNickNameExist(@Body serverRequest: Map<String, String>): Observable<DuplicateNicknameExistCheckResponse>


    @POST("sign_up_request")
    fun signUpRequest(@Body serverRequest: Map<String, String>): Observable<SignUpRequestResponse>

}