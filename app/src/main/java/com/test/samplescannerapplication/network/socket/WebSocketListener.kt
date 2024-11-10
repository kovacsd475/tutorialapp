package com.test.samplescannerapplication.network.socket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

/**
 * Created by kovacsdavid on 06,november,2024
 */
class WebSocketListener(private val messageProcessor: MessageProcessor) :
    okhttp3.WebSocketListener() {
    companion object {
        private const val TAG = "WebSocketListener"
    }


    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d(TAG, "onOpen")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d(TAG, "onMessage bytes")
        messageProcessor.onMessage(bytes)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d(TAG, "onMessage text")
        messageProcessor.onMessage(text)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d(TAG, "onClosing")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(TAG, "onClosed")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d(TAG, "onFailure")
        t.printStackTrace()
    }
}