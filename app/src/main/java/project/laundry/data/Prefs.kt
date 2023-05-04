package project.laundry.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log

class Prefs(context : Context) {

    private val prefNm = "mPref"
    private val prefs = context.getSharedPreferences(prefNm, MODE_PRIVATE)
    private val prefUser = context.getSharedPreferences("User", MODE_PRIVATE)

    var token : String?
        get() = prefs.getString("token", null)
        set(value){
            prefs.edit().putString("token", value).apply()
        }

    var uid : String?
        get() = prefUser.getString("uid", null)
        set(value) {
            prefUser.edit().putString("uid", value).apply()
            Log.d("userInfo", uid!!)
        }

    var userType : String?
        get() = prefUser.getString("user_type", null)
        set(value) {
            prefUser.edit().putString("user_type", value).apply()
            Log.d("userInfo", userType!!)
        }

    var buId : String?
        get() = prefUser.getString("bu_id", null)
        set(value) {
            prefUser.edit().putString("bu_id", value).apply()
        }

//    var reId : String?
//        get() = prefUser.getString("re_id", null)
//        set(value) {
//            prefUser.edit().putString("re_id", value).apply()
//        }

}