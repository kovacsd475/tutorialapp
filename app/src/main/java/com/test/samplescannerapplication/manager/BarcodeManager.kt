package com.test.samplescannerapplication.manager

import com.honeywell.aidc.AidcManager
import com.honeywell.aidc.BarcodeReader
import com.honeywell.aidc.InvalidScannerNameException

object BarcodeManager {
    var barcodeReader: BarcodeReader? = null
        private set

    fun initialize(aidcManager: AidcManager) {
        try {
            barcodeReader = aidcManager.createBarcodeReader()
        } catch (e: InvalidScannerNameException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}