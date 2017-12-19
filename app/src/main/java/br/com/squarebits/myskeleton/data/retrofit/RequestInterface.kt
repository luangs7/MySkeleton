package br.com.luan.myskeleton.data.retrofit

import br.com.luan.myskeleton.data.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.HeaderMap
import retrofit2.http.GET



/**
 * Created by Luan on 17/10/17.
 */
interface RequestInterface {

    @POST("auth")
    abstract fun getToken(@Body json: RequestBody): Call<Void>

    @POST("register")
    abstract fun setRegister(@Body json: RequestBody): Call<Void>

    @POST("password/email")
    abstract fun resetPass(@Body json: RequestBody): Call<Unit>


//    <------------------- GET Request ---------------------------->

    @GET("{cep}/json")
    abstract fun getCep(@Path("cep") cep:String): Call<Void>



}