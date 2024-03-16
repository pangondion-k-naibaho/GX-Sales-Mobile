package com.gxsales.client.model

class Constants {

    interface URL{
        companion object{
            const val BASE_URL = "https://phplaravel-918600-4275378.cloudwaysapps.com/api/"
        }
    }

    interface PREFERENCES{
        companion object{
            const val USER_PREFERENCES = "USER_PREFERENCES"
            const val TOKEN_KEY = "TOKEN_KEY"
        }
    }

    interface STATUS{
        companion object{
            const val STATUS_SUCCESS = "success"
            const val STATUS_ERROR = "error"
        }
    }

}