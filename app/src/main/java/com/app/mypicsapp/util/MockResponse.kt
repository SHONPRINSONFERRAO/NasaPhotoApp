package com.app.mypicsapp.util

object MockResponse {

        const val LOGIN_SUCCESS = "{\"status\":true,\"token\":\"someToken\",\"uniqueId\":112,\"email\":\"success@email.com\"}"
        const val LOGIN_FAILURE = "{\"status\":false,\"email\":\"fail@email.com\"}"
        const val REGISTER_SUCCESS = "{\"status\":true,\"token\":\"someToken\",\"uniqueId\":112,\"email\":\"success@email.com\"}"
        const val REGISTER_FAILURE = "{\"status\":false,\"email\":\"fail@email.com\"}"
        const val SUCCESS_CODE = 200

}