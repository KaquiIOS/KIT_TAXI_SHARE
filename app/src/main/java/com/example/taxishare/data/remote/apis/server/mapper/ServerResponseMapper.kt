/*
 * Created by WonJongSeong on 2019-04-30
 */

package com.example.taxishare.data.remote.apis.server.mapper

import com.example.taxishare.data.model.ServerResponse

object ServerResponseMapper {

    fun toServerResponse(code: Int): ServerResponse =
        when (code) {
            /* Login */
            ServerResponse.LOGIN_SUCCESS.code -> ServerResponse.LOGIN_SUCCESS
            ServerResponse.LOGIN_FAIL.code -> ServerResponse.LOGIN_FAIL
            ServerResponse.NOT_VALIDATED_USER.code -> ServerResponse.NOT_VALIDATED_USER

            /* SignUp */
            ServerResponse.SAME_ID_EXIST.code -> ServerResponse.SAME_ID_EXIST
            ServerResponse.SAME_ID_NOT_EXIST.code -> ServerResponse.SAME_ID_NOT_EXIST
            ServerResponse.SAME_NICKNAME_EXIST.code -> ServerResponse.SAME_NICKNAME_EXIST
            ServerResponse.SAME_NICKNAME_NOT_EXIST.code -> ServerResponse.SAME_NICKNAME_NOT_EXIST
            ServerResponse.SIGN_UP_REQUEST_SUCCESS.code -> ServerResponse.SIGN_UP_REQUEST_SUCCESS
            ServerResponse.SIGN_UP_REQUEST_FAIL.code -> ServerResponse.SIGN_UP_REQUEST_FAIL
            ServerResponse.SIGN_UP_ALREADY_REQUESTED.code -> ServerResponse.SIGN_UP_ALREADY_REQUESTED



            ServerResponse.SUCCESS.code -> ServerResponse.SUCCESS
            ServerResponse.FAIL.code -> ServerResponse.FAIL
            else -> ServerResponse.FAIL
        }

}