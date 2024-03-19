package com.gxsales.client.model

import com.gxsales.client.R
import com.gxsales.client.model.dataclass.response.ItemShopResponse
import com.gxsales.client.model.dataclass.response.ItemSummaryResponse

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

    interface LEAD_STATUS{
        companion object{
            const val SCHEDULED = "Scheduled"
            const val CONSIDERATION = "Consideration"
            const val JUNK = "Junk"
        }
    }

    interface LEAD_PROBABILITY{
        companion object{
            const val PENDING = "Pending"
            const val CONVERTED = "Converted"
            const val CANCEL = "Cancel"
        }
    }

    interface DUMMY_DATA{
        companion object{
            fun getListItemShop(): List<ItemShopResponse> = listOf(
                ItemShopResponse(
                    R.drawable.item1,
                    "A810R AC1200 Router",
                    "56.000.000.000",
                    3,
                    "Onu",
                    "56.600"
                ),
                ItemShopResponse(
                    R.drawable.item2,
                    "A810R AC1200 Router",
                    "56.000.000.000",
                    3,
                    "Baygon",
                    "56.600"
                ),
                ItemShopResponse(
                    R.drawable.item3,
                    "A810R AC1200 Router",
                    "56.000.000.000",
                    3,
                    "Baygon",
                    "56.600"
                ),
                ItemShopResponse(
                    R.drawable.item3,
                    "A810R AC1200 Router",
                    "56.000.000.000",
                    3,
                    "Baygon",
                    "56.600"
                ),
                ItemShopResponse(
                    R.drawable.item4,
                    "A810R AC1200 Router",
                    "56.000.000.000",
                    3,
                    "Baygon",
                    "56.600"
                ),
            )

            fun getListItemSummary(): List<ItemSummaryResponse> = listOf(
                ItemSummaryResponse(15 ,"Total All Leads"),
                ItemSummaryResponse(7 ,"Total Leads Cancel"),
                ItemSummaryResponse(5 ,"Total Leads Pending"),
                ItemSummaryResponse(37 ,"Total Leads Converted")
            )
        }
    }

}