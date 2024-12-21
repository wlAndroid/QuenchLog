package com.example.innerdex

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface InnHiApi {

    @POST("elision/halogen/scripps")
    suspend fun fhhan(@Body request: RequestBody): Response<ResponseBody?>?

    @GET
    suspend fun tfgh(
        @Url url: String = "https://bootii.oss-ap-southeast-1.aliyuncs.com/Bootii"
    ): Response<ResponseBody?>?

}