package com.app.quench.log.setting

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HitApi {

    @POST("elision/halogen/scripps")
    suspend fun hfawfnsna(@Body request: RequestBody): Response<ResponseBody?>?

}