package br.com.luan.myskeleton.data.request

import android.app.Activity
import android.content.Context
import br.com.luan.myskeleton.extras.Utils
import br.com.luan.myskeleton.data.retrofit.ApiManager
import br.com.luan.myskeleton.data.retrofit.CustomCallback
import br.com.luan.myskeleton.data.retrofit.RequestInterface
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by Luan on 07/08/17.
 */

class SignupRequestActivity(private val activity: Activity, private val mListener: RequestListener) {
    private val context: Context

    init {
        this.context = activity.baseContext
    }

    interface RequestListener {
//        fun onSuccess(user: User)
        fun onError(error: String)
    }


//
//    fun register(user: User) {
//
//        val json = Gson().toJson(user)
//        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
//
//        ApiManager(context)
//                .retrofit
//                .create(RequestInterface::class.java)
//                .setRegister(body)
//                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<User> {
//                    override fun onResponse(response: User?) {
//                        mListener.onSuccess(response!!)
//                    }
//
//                    override fun onFailure(t: Throwable?) {
//                        mListener.onError(t!!.message!!)
//                    }
//
//                    override fun onRetry(t: Throwable?) {
//                        register(user)
//                    }
//                }))
//    }
//
//
//
//    fun update(user: User) {
//
//        val auth = Utils().getSharedAuth(context)
//
//        val header = HashMap<String,String>()
//        header.put("Authorization", "Bearer " + auth!!.authorization)
//
//        val json = Gson().toJson(user)
//        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
//
//        ApiManager(context)
//                .retrofit
//                .create(RequestInterface::class.java)
//                .updRegister(header,body,auth.id!!)
//                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<User> {
//                    override fun onResponse(response: User?) {
//                        mListener.onSuccess(response!!)
//                    }
//
//                    override fun onFailure(t: Throwable?) {
//                        mListener.onError(t!!.message!!)
//                    }
//
//                    override fun onRetry(t: Throwable?) {
//                        register(user)
//                    }
//                }))
//    }


}