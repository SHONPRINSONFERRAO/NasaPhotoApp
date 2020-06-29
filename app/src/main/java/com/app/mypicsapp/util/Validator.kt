package com.app.mypicsapp.util

import java.util.regex.Pattern

class Validator {
    companion object {
        fun isEmailValid(email: String): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun isPasswordValid(password: String): Boolean {
            return password.length in 6..12
        }

        fun isAgeinRange(age: Int): Boolean {
            return age in 18..99
        }

        fun isPasswordMatching(password: String, confirmPassword: String): Boolean {
            return password == confirmPassword
        }
    }
}