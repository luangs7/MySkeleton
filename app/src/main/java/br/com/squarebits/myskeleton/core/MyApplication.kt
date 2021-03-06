package br.com.luan.myskeleton.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log


/**
 * Created by luan on 1/15/15.
 */
class MyApplication : Application() {


    val context: Context
        get() = this
    lateinit private var sInstance: MyApplication


    override fun onCreate() {
        //        MultiDex.install(getApplicationContext());
        super.onCreate()
        sInstance = this

        //        Realm.init(this);
        //        try{
        //            FirebaseApp.initializeApp(this);
        //            FirebaseMessaging.getInstance().subscribeToTopic("general");
        //        }catch (Exception e){
        //            Log.e("teste",e.getMessage());
        //        }




    }


    /*  public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni == null ? false : true;
    } */

    val isNetworkConnected: Boolean
        get() {
            try {
                val nInfo = getSystemService(
                        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                nInfo.activeNetworkInfo.isConnectedOrConnecting

                val cm = getSystemService(
                        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = cm.activeNetworkInfo
                return if (netInfo != null && netInfo.isConnectedOrConnecting) {

                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                return false
            }

        }




    enum class TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER
        // Tracker used by all ecommerce transactions from a company.
    }


    fun applicationName(): String {
        try {

            val appR = context.resources
            val txt = appR.getText(appR.getIdentifier("app_name", "string", context.packageName))
            return txt.toString()
        } catch (ex: Exception) {
            Log.e("applicationColor", ex.message)
            return MyApplication::class.java.`package`.name.replace("br.com.", "")
        }


    }

    override fun onTerminate() {
        super.onTerminate()
    }


}
