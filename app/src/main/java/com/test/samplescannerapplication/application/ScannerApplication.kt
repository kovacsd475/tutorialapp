package com.test.samplescannerapplication.application

import android.app.Application
import android.content.Context
import android.util.Log

/**
 * Created by kovacsdavid on 06,november,2024
 */
class ScannerApplication : Application() {
    private val TAG = ScannerApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
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
}