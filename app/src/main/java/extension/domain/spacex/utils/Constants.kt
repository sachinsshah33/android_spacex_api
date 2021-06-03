package extension.domain.spacex.utils

import com.google.gson.GsonBuilder
import extension.domain.spacex.data.network.DateDeserializer
import java.util.*


object Constants {
    const val BASE_URL = "https://api.spacexdata.com/v4/"

    //used for MongoDB
    const val FALCON_9_ID = "5e9d0d95eda69973a809d1ec"
    const val DESC = -1
    //used for MongoDB

    const val DEFAULT_LIMIT = 20 //default paging size
    const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val SHORT_DATE_FORMAT = "dd-MM-yyyy"
    const val APPLICATION_JSON = "application/json"
    val APPLICATION_JSON_UTF8 = "$APPLICATION_JSON; charset=${Charsets.UTF_8}"

    val gson = GsonBuilder()
        .setDateFormat(DATE_TIME_FORMAT)
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()
}