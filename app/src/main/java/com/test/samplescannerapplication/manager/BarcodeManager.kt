package com.test.samplescannerapplication.manager

import com.honeywell.aidc.AidcManager
import com.honeywell.aidc.BarcodeReader
import com.honeywell.aidc.InvalidScannerNameException

object BarcodeManager {
    private var barcodeReader: BarcodeReader? = null

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