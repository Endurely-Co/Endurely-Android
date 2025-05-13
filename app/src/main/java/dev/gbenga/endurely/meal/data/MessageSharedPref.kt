package dev.gbenga.endurely.meal.data

import android.content.SharedPreferences

class MessageSharedPref(private val sharedPreferences: SharedPreferences) {

    companion object{
        const val MSG_KEY = "MessageSharedPref.UI_MSG"
        const val RELOAD_MSG = "MessageSharedPref.RELOAD_MSG"
    }

    fun setMessage(msg: String){
        setStr(MSG_KEY, msg)
    }

    fun getMessage() = sharedPreferences.getString(MSG_KEY, null)

    fun setReload(){
        setBool(RELOAD_MSG, true)
    }

    fun isReload() = sharedPreferences.getBoolean(RELOAD_MSG, false)

    fun clear() = sharedPreferences.edit().remove(MSG_KEY).apply()

    private fun setStr(key: String, value: String){
        sharedPreferences.edit().putString(key, value).apply()
    }



    private fun setBool(key: String, value: Boolean){
        sharedPreferences.edit().putBoolean(key, value).apply()
    }
}