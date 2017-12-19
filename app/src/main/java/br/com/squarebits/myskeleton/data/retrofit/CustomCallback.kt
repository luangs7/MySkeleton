package br.com.luan.myskeleton.data.retrofit

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View

import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import br.com.luan.myskeleton.R
import br.com.luan.myskeleton.data.model.BaseRequest
import br.com.luan.myskeleton.extras.Utils
import dmax.dialog.SpotsDialog


/**
 * Created by Luan on 17/10/17.
 */
@SuppressWarnings("unused")
class CustomCallback<T> : Callback<T> {

    var activity: Activity
    lateinit var  dialog: SpotsDialog
    lateinit var onResponse: OnResponse<T>
    lateinit var viewLayout: View
    
    constructor(activity: Activity){
        this.activity = activity
        dialog = SpotsDialog(activity, R.style.AppTheme_AlertDialog)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setMessage("Buscando dados...")
        dialog.show()

        this.onResponse = onResponse
    }

    constructor(activity: Activity, onResponse: OnResponse<T>){
        this.activity = activity
        dialog = SpotsDialog(activity, R.style.AppTheme_AlertDialog)
        dialog.setCancelable(false)
        dialog.setMessage("Buscando dados...")
        dialog.show()
        this.onResponse = onResponse
    }


    constructor(activity: Activity, viewLayout: View, onResponse: OnResponse<T>) {
        this.activity = activity
        dialog = SpotsDialog(activity, R.style.AppTheme_AlertDialog)
        dialog.setCancelable(false)
        dialog.setMessage("Buscando dados...")
        dialog.show()
        this.onResponse = onResponse
        this.viewLayout = viewLayout
    }


    constructor(activity: Activity, onResponse: OnResponse<T>, loadDialog: Boolean){
        this.activity = activity
        if (loadDialog) {
            try {
                dialog = SpotsDialog(activity, R.style.AppTheme_AlertDialog)
                dialog.setCancelable(false)
                dialog.setMessage("Buscando dados...")
                dialog.show()
            } catch (ex: Throwable) {
                Log.e(" dialog!!", "CustomCallback: ", ex)
            }

        } else {

        }

        this.onResponse = onResponse
    }



    constructor(activity: Activity,  Text: String, onResponse: OnResponse<T>)  {
        this.activity = activity
        dialog = SpotsDialog(activity, R.style.AppTheme_AlertDialog)
        dialog.setCancelable(false)
        dialog.setMessage(Text)
        dialog.show()
        this.onResponse = onResponse
    }


    constructor(activity: Activity,  Text: String, viewLayout: View, onResponse: OnResponse<T>)  {
        this.activity = activity
        dialog = SpotsDialog(activity, R.style.AppTheme_AlertDialog)
        dialog.setCancelable(false)
        dialog.setMessage( Text)
        dialog.show()
        this.onResponse = onResponse
        this.viewLayout = viewLayout
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        var error = ""
        try {
            dialog.dismiss()
        } catch (e: Exception) {

        }

        if (response!!.isSuccessful())
            onResponse.onResponse(response.body())


        else {
            if (response.code() == 202) {
                try {
                    onResponse.onResponse(response.body())
                } catch (ex: Exception) {
                    try {
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                    } catch (ex2: Exception) {
                        onResponse.onFailure(Throwable(activity.getResources().getString(R.string.error401)))
                    }

                }

            }
            if (response.code() == 401) {
                error = response.errorBody().string()
                try {
//                    if(!Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message.equals("Login e/ou senha inválidos."))
//                        onBottomSheetMessage(activity.getResources().getString(R.string.error401),"Infelizmente, que sua sessão expirou, faça o login novamente.",false, LoginActivity())
//                    else
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                } catch (ex: Exception) {
                    try {
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                    } catch (ex2: Exception) {
                        onResponse.onFailure(Throwable(activity.getResources().getString(R.string.error401)))
                    }

                }

            } else if (response.code() == 404) {
                try {
                    error = response.errorBody().string()
                    onResponse.onResponse(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java) as T)
                } catch (ex: Exception) {
                    try {
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                    } catch (ex2: Exception) {
                        onResponse.onFailure(Throwable(activity.getResources().getString(R.string.error404)))
                    }

                }

            } else if (response.code() == 422) {
                try {

                    error = response.errorBody().string()
                    var message = ""

                    val jsnobject = JSONObject(error)
                    val jsonArray = jsnobject.getJSONArray("reason")
                    for (i in 0..jsonArray.length() - 1) {
                        message = jsonArray.get(i).toString()
                    }
                    onResponse.onFailure(Throwable(message))
                } catch (ex: Exception) {
                    try {
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                    } catch (ex2: Exception) {
                        onResponse.onFailure(Throwable("Ocorreu um erro"))
                    }

                }

            }
            else if (response.code() == 406) {
                try {
                    error = response.errorBody().string()
                    var message = ""

                    val jsnobject = JSONObject(error)

                    onResponse.onFailure(Throwable(jsnobject.getString("errors")))
                } catch (ex: Exception) {
                    try {
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                    } catch (ex2: Exception) {
                        onResponse.onFailure(Throwable("Ocorreu um erro"))
                    }

                }

            }else if (response.code() == 500) {
                try {
                    onResponse.onFailure(Throwable(activity.getResources().getString(R.string.error500)))
                } catch (ex: Exception) {
                    try {
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                    } catch (ex2: Exception) {
                        onResponse.onFailure(Throwable("Ocorreu um erro"))
                    }

                }

            } else if (response.code() == 429) {
                try {
                    onResponse.onRetry(Throwable(activity.getResources().getString(R.string.error401)))
                } catch (ex: Exception) {
                    try {
                        onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
                    } catch (ex2: Exception) {
                        onResponse.onFailure(Throwable("Ocorreu um erro"))
                    }

                }

            } else {
                try {
                    error = response.errorBody().string()
                    val errorBody = Gson().fromJson<BaseRequest>(error, BaseRequest::class.java)
                    onResponse.onFailure(Throwable(errorBody.message))
                } catch (ex: Exception) {
                    onResponse.onFailure(Throwable("Ocorreu um erro"))
                }

            }
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        var problem: String


        if(::dialog.isInitialized){
            dialog.dismiss()
        }


        if(!isNetworkAvailable(activity.baseContext))
            problem = activity.resources.getString(R.string.noConnect)
        else
            problem = "Houve um erro ao conectar com o servidor."

            //Cria o gerador do AlertDialog
            val builder = AlertDialog.Builder(activity, R.style.AppTheme_AlertDialog)
            //define o titulo
            builder.setTitle(problem)
            //define a mensagem
            builder.setMessage("Gostaria de tentar novamente?")
            //define um botão como positivo
            builder.setPositiveButton("Sim") { arg0, _ ->
                arg0.dismiss()
                onResponse.onRetry(t!!)
            }
            //define um botão como negativo.
            builder.setNegativeButton("Não") { arg0, _ ->
                arg0.dismiss()
                onResponse.onFailure(t!!)
            }
            //cria o AlertDialog
            val alerta = builder.create()
            //Exibe
            try {
                alerta.show()
            } catch (e: Exception) {

            }


    }


    fun isNetworkAvailable(activity: Context?): Boolean {
        if (activity == null) {
            return false
        }
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    interface OnResponse<T> {
        fun onResponse(response: T?)
        fun onFailure(t: Throwable?)
        fun onRetry(t: Throwable?)
    }



    fun onBottomSheetMessage(title: String, text: String,  clearTop: Boolean, mActivity: Activity) {
        var inflater =  activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater

        val view = inflater.inflate(R.layout.alert_bottom, null)
        val alerttitle = view.findViewById(R.id.title) as TextView
        val content = view.findViewById(R.id.text) as TextView
        val positive = view.findViewById(R.id.positive) as Button

        content.text = text
        alerttitle.text = title
        val mBottomSheetDialog = Dialog(activity, R.style.MaterialDialogSheet)

        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(false)
        mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()

        positive.setOnClickListener(View.OnClickListener {
            activity.finishAffinity()
            activity.startActivity(Intent(activity,mActivity.javaClass))
//            Utils().clearShared(activity)
            mBottomSheetDialog.dismiss()
        })
    }
}

