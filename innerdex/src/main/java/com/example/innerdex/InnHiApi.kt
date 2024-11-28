package com.example.innerdex

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InnHiApi {

    @POST("elision/halogen/scripps")
    suspend fun fhhan(@Body request: RequestBody): Response<ResponseBody?>?

}