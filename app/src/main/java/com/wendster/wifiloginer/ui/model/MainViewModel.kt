package com.wendster.wifiloginer.ui.model

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import lantian.nolitter.utilitiy.DataStoreUtil
import org.json.JSONObject


class MainViewModel(application: Application) : AndroidViewModel(application) {
    enum class WifiLoginStatus {
        IDLE, LOADING, SUCCESS, IDENTITY_FAILED, OTHER_ERRORS, WIFI_NOT_CONNECTED, GET_NETWORK_STATUS_FAILED
    }

    private var activity: Application = application
    private var dataStore: DataStoreUtil = DataStoreUtil.apply { initialize(activity) }
    var appTheme: MutableState<String> = mutableStateOf(dataStore.getPreference("theme", "default"))

    val appUsername: MutableState<String> = mutableStateOf(dataStore.getPreference("username", ""))
    val appPassword: MutableState<String> = mutableStateOf(dataStore.getPreference("password", ""))
    val passwordVisible: MutableState<Boolean> = mutableStateOf(false)
    val rememberPassword: MutableState<Boolean> = mutableStateOf(dataStore.getPreference("remember_password", true))
    val wifiLoginStatus: MutableState<WifiLoginStatus> = mutableStateOf(WifiLoginStatus.IDLE)

    fun onRememberPasswordChange() {
        rememberPassword.value = !rememberPassword.value
        dataStore.setPreference("remember_password", rememberPassword.value)
        dataStore.setPreference("password", "")
    }

    fun doWifiLogin() {
        // Save or clear the preference
        dataStore.setPreference("username", appUsername.value)
        dataStore.setPreference("password", if (rememberPassword.value) appPassword.value else "")

        // Check network status and set the status to loading
        wifiLoginStatus.value = getNetworkStatus()
        if (wifiLoginStatus.value != WifiLoginStatus.LOADING) return

        // Construct the POST request
        val uri = "https://controller.shanghaitech.edu.cn:8445/PortalServer/Webauth/webAuthAction!login.action"
        val responseListener =  Response.Listener<String> { response ->
            val result = JSONObject(response.toString())
            wifiLoginStatus.value = if (result.optBoolean("success")) WifiLoginStatus.SUCCESS else WifiLoginStatus.IDENTITY_FAILED
        }
        val errorListener = Response.ErrorListener { error -> wifiLoginStatus.value = WifiLoginStatus.OTHER_ERRORS }
        val stringRequest: StringRequest = object : StringRequest(Method.POST, uri , responseListener, errorListener) {
            override fun getParams(): Map<String, String> = mapOf("userName" to appUsername.value, "password" to appPassword.value)
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