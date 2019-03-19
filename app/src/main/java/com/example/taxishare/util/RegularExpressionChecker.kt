/*
 * Created by WonJongSeong on 2019-03-18
 */

package com.example.taxishare.util

import java.util.regex.Pattern

class RegularExpressionChecker {
    companion object {

        const val EMAIL_EXPRESSION_RULE =
            "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$"

        fun checkEmailValidation(expression: String): Boolean = (
                Pattern.compile(EMAIL_EXPRESSION_RULE).matcher(expression).find())
    }
}