/*
 * Created by WonJongSeong on 2019-03-18
 */

package com.example.taxishare.util

import java.util.regex.Pattern

class RegularExpressionChecker {
    companion object {

        private const val EMAIL_EXPRESSION_RULE =
            "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$"


        private const val PASSWORD_EXPRESSION_RULE =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$"

        private const val STUDENT_ID_EXPRESSION_RULE =
            "^20[\\d]{6}$"

        private const val NICKNAME_EXPRESSION_RULE =
            "^[a-zA-Z가-힣-_.]{3,}$"

        fun checkEmailValidation(expression: String): Boolean =
            Pattern.compile(EMAIL_EXPRESSION_RULE).matcher(expression).find()

        fun checkPasswordValidation(expression: String): Boolean =
            Pattern.compile(PASSWORD_EXPRESSION_RULE).matcher(expression).find()

        fun checkStudentIdValidation(expression: String): Boolean =
            Pattern.compile(STUDENT_ID_EXPRESSION_RULE).matcher(expression).find()

        fun checkNicknameValidation(expression: String): Boolean =
            Pattern.compile(NICKNAME_EXPRESSION_RULE).matcher(expression).find()

    }
}