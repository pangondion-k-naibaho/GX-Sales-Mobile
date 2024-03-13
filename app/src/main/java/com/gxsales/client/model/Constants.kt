package com.gxsales.client.model

class Constants {

    interface URL{
        companion object{
            const val BASE_URL = "https://phplaravel-918600-4275378.cloudwaysapps.com/api/"
        }
    }

    interface PREFERENCES{
        companion object{
            const val LOGIN_PREFERENCES = "LOGIN_PREFERENCES"
            const val TOKEN_KEY = "TOKEN_KEY"
        }
    }

    interface LOGIN_STATUS{
        companion object{
            const val LOGIN_SUCCESS = "success"
            const val LOGIN_ERROR = "error"
        }
    }

}