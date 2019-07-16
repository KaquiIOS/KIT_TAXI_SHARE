/*
 * Created by WonJongSeong on 2019-04-30
 */

package com.example.taxishare.data.model

enum class ServerResponse(val code: Int, val description: String) {

    /* 기본적인 성공 */
    SUCCESS(1100, "Success"),
    /* 기본적인 실패 */
    FAIL(1400, "Fail"),

    /* Login */
    LOGIN_SUCCESS(1701, "LoginSuccess"),
    LOGIN_FAIL(1702, "LoginFail"),
    NOT_VALIDATED_USER(1703, "NotValidatedUser"),

    /* IdDuplicationCheckRequest */
    SAME_ID_EXIST(1501, "SameIdExist"),
    SAME_ID_NOT_EXIST(1502, "SameIdNotExist"),

    /* NicknameDuplicationCheckRequest */
    SAME_NICKNAME_EXIST(1601, "SameNicknameExist"),
    SAME_NICKNAME_NOT_EXIST(1602, "SameNicknameNotExist"),

    /* SignUpRequest */
    SIGN_UP_REQUEST_SUCCESS(1801, "SignUpRequestSuccess"),
    SIGN_UP_REQUEST_FAIL(1802, "SignUpRequestFail"),
    SIGN_UP_ALREADY_REQUESTED(1803, "AlreadyRequested"),

    /* TaxiRegisterRequest */
    TAXI_SHARE_REGISTER_REQUEST_SUCCESS(1901, "TaxiShareRegisterSuccess"),
    TAXI_SHARE_REGISTER_REQUEST_FAIL(1902, "TaxiShareRegisterFail");

    companion object{
        fun fromServerResponseCode(code : Int) = values().first { it.code == code }
    }

}