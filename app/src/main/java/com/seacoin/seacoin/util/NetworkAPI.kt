package com.seacoin.seacoin.util

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by eka on 2018. 1. 21..
 */
interface NetworkAPI {

    @GET("/users")
    fun login(@Query("id") id: String, @Query("password") password: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/users")
    fun register(@Field("id") id: String,
                 @Field("password") password: String,
                 @Field("name") name: String,
                 @Field("birthday") birth: String,
                 @Field("cardNumber") cardNum: String,
                 @Field("cardPassword") cardPassword: String,
                 @Field("cardExpiary") cardExpiry: String): Call<ResponseBody>

    @GET("/coin")
    fun getCoins(): Call<ResponseBody>

    @GET("/users/coin")
    fun getCounts(@Query("id") id: String): Call<ResponseBody>

    @GET("/pay")
    fun pay(@Query("id") id: String,
            @Query("num") num: Int,
            @Query("coin") coin: String): Call<ResponseBody>

}