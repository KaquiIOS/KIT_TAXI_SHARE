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
    TAXI_SHARE_REGISTER_REQUEST_FAIL(1902, "TaxiShareRegisterFail"),

    COMMENT_REGISTER_REQUEST_SUCCESS(2001, "CommentRegisterSuccess"),
    COMMENT_REGISTER_REQUEST_FAIL(2002, "CommentRegisterFail"),

    /* ParticipateTaxiShareRequest */
    PARTICIPATE_TAXI_SHARE_SUCCESS(2011, "ParticipateTaxiShareSuccess"),
    PARTICIPATE_TAXI_SHARE_FAIL(2012, "ParticipateTaxiShareFail"),

    /* CommentRemoveRequest */
    COMMENT_REMOVE_SUCCESS(2021, "CommentRemoveSuccess"),
    COMMENT_REMOVE_FAIL(2022, "CommentRemoveFail"),

    TAXISHARE_REMOVE_SUCCESS(2031, "TaxiShareRemoveSuccess"),
    TAXISHARE_REMOVE_FAIL(2032, "TaxiShareRemoveFail"),

    TAXISHARE_MODIFY_SUCCESS(2041, "TaxiShareRemoveSuccess"),
    TAXISHARE_MODIFY_FAIL(2042, "TaxiShareRemoveFail"),

    TAXISHARE_LEAVE_SUCCESS(2051, "TaxiShareLeaveSuccess"),
    TAXISHARE_LEAVE_FAIL(2052, "TaxiShareLeaveFail"),

    DETAIL_TAXISHARE_LOAD_SUCCESS(2061, "DetailTaxiShareSuccess"),
    DETAIL_TAXISHARE_LOAD_FAIL(2062, "DetailTaxiShareFail"),
    DETAIL_TAXISHARE_DELETED(2063, "DetailTaxiShareDeleted");


    companion object{
        fun fromServerResponseCode(code : Int) = values().first { it.code == code }
    }

}