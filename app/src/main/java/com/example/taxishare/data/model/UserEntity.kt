/*
 * Created by WonJongSeong on 2019-03-24
 * 회원가입 및 서버에서 저장하고 있을 User Entity
 */

package com.example.taxishare.data.model

data class UserEntity(
    val studentId: Int, val pw: String,
    val nickname: String, val email: String,
    val major: Int
)