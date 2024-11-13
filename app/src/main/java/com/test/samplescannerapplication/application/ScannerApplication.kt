package com.test.samplescannerapplication.application

import android.app.Application
import android.content.Context
import android.util.Log
import com.honeywell.aidc.AidcManager
import com.test.samplescannerapplication.manager.BarcodeManager

/**
 * Created by kovacsdavid on 06,november,2024
 */
class ScannerApplication : Application() {
    private val TAG = ScannerApplication::class.java.simpleName
    private var barcodeManager: AidcManager? = null


    override fun onCreate() {
        super.onCreate()
        AidcManager.create(this) { m ->
            barcodeManager = m
            BarcodeManager.initialize(m)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //Set language
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(TAG, "onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(TAG, "onTrimMemory Level: $level ")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate ")
    }

    fun closeManager() {
        barcodeManager?.close()
    }
}