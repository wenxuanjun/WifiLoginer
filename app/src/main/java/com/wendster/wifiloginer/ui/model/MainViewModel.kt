package com.wendster.wifiloginer.ui.model

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.wendster.wifiloginer.utilitiy.Constants
import lantian.nolitter.utilitiy.DataStoreUtil
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    enum class WifiLoginStatus {
        IDLE, LOADING, SUCCESS, EMPTY_USERNAME, EMPTY_PASSWORD,
        IDENTITY_FAILED, OTHER_ERRORS, WIFI_NOT_CONNECTED, GET_NETWORK_STATUS_FAILED
    }

    private var activity: Application = application
    private var dataStore: DataStoreUtil = DataStoreUtil.apply { initialize(activity) }

    var appTheme: MutableState<String> = mutableStateOf(dataStore.getPreference("theme", "default"))
    val appUsername: MutableState<String> = mutableStateOf(dataStore.getPreference("username", ""))
    val appPassword: MutableState<String> = mutableStateOf(dataStore.getPreference("password", ""))
    val passwordVisible: MutableState<Boolean> = mutableStateOf(false)
    val rememberPassword: MutableState<Boolean> = mutableStateOf(dataStore.getPreference("remember_password", true))
    val customLoginUri: MutableState<Boolean> = mutableStateOf(dataStore.getPreference("custom_login_uri", false))
    val loginUri: MutableState<String> = mutableStateOf(dataStore.getPreference("login_uri", Constants.defaultLoginUri))
    val wifiLoginStatus: MutableState<WifiLoginStatus> = mutableStateOf(WifiLoginStatus.IDLE)
    val errorLoginMessage: MutableState<String> = mutableStateOf("")

    fun intentToWebsite(uri: String) {
        ContextCompat.startActivity(activity, Intent(Intent.ACTION_VIEW, Uri.parse(uri)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), null)
    }

    fun onRememberPasswordChange() {
        rememberPassword.value = !rememberPassword.value
        dataStore.setPreference("remember_password", rememberPassword.value)
        dataStore.setPreference("password", "")
    }

    fun doWifiLogin() {
        // If username or password is empty
        if (appUsername.value.isEmpty()) { wifiLoginStatus.value = WifiLoginStatus.EMPTY_USERNAME; return }
        if (appPassword.value.isEmpty()) { wifiLoginStatus.value = WifiLoginStatus.EMPTY_PASSWORD; return }

        // Save or clear the preference
        dataStore.setPreference("username", appUsername.value)
        dataStore.setPreference("password", if (rememberPassword.value) appPassword.value else "")

        // Check network status and set the status to loading
        wifiLoginStatus.value = getNetworkStatus()
        if (wifiLoginStatus.value != WifiLoginStatus.LOADING) return

        // Construct the POST request
        val responseListener =  Response.Listener<String> { response ->
            val result = JSONObject(response.toString())
            if (result.optBoolean("success")) wifiLoginStatus.value = WifiLoginStatus.SUCCESS
            else if (result.optString("message").contains("用户名或密码错误")) wifiLoginStatus.value = WifiLoginStatus.IDENTITY_FAILED
            else { wifiLoginStatus.value = WifiLoginStatus.OTHER_ERRORS; errorLoginMessage.value = result.optString("message") }
        }
        val errorListener = Response.ErrorListener { error ->
            wifiLoginStatus.value = WifiLoginStatus.OTHER_ERRORS
            errorLoginMessage.value = error.message.toString()
        }
        val stringRequest: StringRequest = object : StringRequest(Method.POST,if (customLoginUri.value) loginUri.value else Constants.defaultLoginUri, responseListener, errorListener) {
            override fun getParams(): Map<String, String> = mapOf("userName" to appUsername.value, "password" to appPassword.value, "authLan" to "zh_CN")
        }

        // Begin do the POST request
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    private fun getNetworkStatus(): WifiLoginStatus {
        val connectivityManager = getSystemService(activity, ConnectivityManager::class.java) ?: return WifiLoginStatus.GET_NETWORK_STATUS_FAILED
        val activeNetwork = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return WifiLoginStatus.GET_NETWORK_STATUS_FAILED
        if (!activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return WifiLoginStatus.WIFI_NOT_CONNECTED
        return WifiLoginStatus.LOADING
    }
}