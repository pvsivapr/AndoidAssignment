package com.nytimes.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface INYTimesService {

    @GET("svc/topstories/v2/{type}.json?api-key=dDdTIvBJI9Csu4kcGNlU32I0GBAmGXct")
    fun getNYArtsItems(@Path("type") type: String): Call<NYItemsListModel>
}