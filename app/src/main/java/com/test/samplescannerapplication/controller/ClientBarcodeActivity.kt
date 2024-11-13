package com.test.samplescannerapplication.controller

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.honeywell.aidc.BarcodeFailureEvent
import com.honeywell.aidc.BarcodeReadEvent
import com.honeywell.aidc.BarcodeReader
import com.honeywell.aidc.TriggerStateChangeEvent
import com.honeywell.aidc.UnsupportedPropertyException
import com.test.samplescannerapplication.R
import com.test.samplescannerapplication.databinding.ActivityClientBarcodeBinding
import com.test.samplescannerapplication.manager.BarcodeManager


class ClientBarcodeActivity : AppCompatActivity(), BarcodeReader.BarcodeListener,
    BarcodeReader.TriggerListener {
    private lateinit var binding: ActivityClientBarcodeBinding
    private var barcodeReader: BarcodeReader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClientBarcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestedOrientation = if (Build.MODEL.startsWith("VM1A"))
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setupBarcodeReader()
    }

    private fun setupBarcodeReader() {
        // get bar code instance from BarcodeManager
        barcodeReader = BarcodeManager.barcodeReader
        //  Setup the nonNull object
        barcodeReader?.let { theBarcodeReader ->
            // register bar code event listener
            theBarcodeReader.addBarcodeListener(this)

            // set the trigger mode to client control
            try {
                theBarcodeReader.setProperty(
                    BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                    BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL
                )

                // barcodeReader.setProperty(BarcodeReader.PROPERTY_DATA_PROCESSOR_LAUNCH_BROWSER,true);
            } catch (e: UnsupportedPropertyException) {
                Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_SHORT).show()
            }
            // register trigger state change listener
            theBarcodeReader.addTriggerListener(this)

            val properties: MutableMap<String, Any> = HashMap()
            // Set Symbologies On/Off
            properties[BarcodeReader.PROPERTY_CODE_128_ENABLED] = true
            properties[BarcodeReader.PROPERTY_GS1_128_ENABLED] = true
            properties[BarcodeReader.PROPERTY_QR_CODE_ENABLED] = true
            properties[BarcodeReader.PROPERTY_CODE_39_ENABLED] = true
            properties[BarcodeReader.PROPERTY_DATAMATRIX_ENABLED] = true
            properties[BarcodeReader.PROPERTY_UPC_A_ENABLE] = true
            properties[BarcodeReader.PROPERTY_EAN_13_ENABLED] = false
            properties[BarcodeReader.PROPERTY_AZTEC_ENABLED] = false
            properties[BarcodeReader.PROPERTY_CODABAR_ENABLED] = false
            properties[BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED] = false
            properties[BarcodeReader.PROPERTY_PDF_417_ENABLED] = false
            //properties.put(BarcodeReader.PROPERTY_DATA_PROCESSOR_LAUNCH_BROWSER,false);
            // Set Max Code 39 barcode length
            properties[BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH] = 10
            // Turn on center decoding
            properties[BarcodeReader.PROPERTY_CENTER_DECODE] = true
            // Disable bad read response, handle in onFailureEvent
            properties[BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED] = false
            /*            // Sets time period for decoder timeout in any mode
                     properties.put(BarcodeReader.PROPERTY_DECODER_TIMEOUT,  400);
                     try {
                         properties.put(BarcodeReader.PROPERTY_TRIG_PRES_MODE, true);
                         properties.put(BarcodeReader.PROPERTY_TRIG_PRES_AIMER_ON, true);
                         properties.put(BarcodeReader.PROPERTY_TRIG_PRES_ILLUM_ON_TIME, 3000);
                         properties.put(BarcodeReader.PROPERTY_TRIG_PRES_IDLE_ILLUM_ON, true);
                         properties.put(BarcodeReader.PROPERTY_TRIG_PRES_IDLE_ILLUM_ON_INTENSITY, 50);
                         barcodeReader.softwareTrigger(true);

                     } catch (Exception exception) {
                         Log.d("ClientBarcode", exception.getMessage());
                     }*/
            properties[BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED] = true
            properties[BarcodeReader.PROPERTY_INTERLEAVED_25_REDUNDANCY_MODE] = 10
            properties[BarcodeReader.PROPERTY_LINEAR_VOID_HANDLING] = false
            // Apply the settings
            theBarcodeReader.setProperties(properties)
        }
    }


    /**
     *
     *  BarcodeReader.BarcodeListener
     *
     */
    override fun onBarcodeEvent(event: BarcodeReadEvent?) {
        event?.let {
            val list: MutableList<String> = ArrayList()
            list.add("Barcode data: " + it.barcodeData)
            list.add("Character Set: " + it.charset)
            list.add("Code ID: " + it.codeId)
            list.add("AIM ID: " + it.aimId)
            list.add("Timestamp: " + it.timestamp)

            runOnUiThread {
                val dataAdapter = ArrayAdapter(
                    this@ClientBarcodeActivity, android.R.layout.simple_list_item_1, list
                )
                binding.listViewBarcodeData.setAdapter(dataAdapter)
            }
        }
    }

    override fun onFailureEvent(p0: BarcodeFailureEvent?) {
        runOnUiThread {
            Toast.makeText(
                this@ClientBarcodeActivity,
                "No data",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    /**
     *
     *
     */
    override fun onTriggerEvent(p0: TriggerStateChangeEvent?) {
    }


    public override fun onPause() {
        super.onPause()
        // release the scanner claim so we don't get any scanner
        // notifications while paused.
        barcodeReader?.release()
    }

    public override fun onDestroy() {
        super.onDestroy()
        barcodeReader?.apply {
            // unregister barcode event listener
            removeBarcodeListener(this@ClientBarcodeActivity)
            // unregister trigger state change listener
            removeTriggerListener(this@ClientBarcodeActivity)
        }
    }


}