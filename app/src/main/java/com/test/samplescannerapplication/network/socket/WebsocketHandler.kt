package com.test.samplescannerapplication.network.socket

import com.test.samplescannerapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

/**
 * Created by kovacsdavid on 06,november,2024
 */
object WebsocketHandler {
    private val okHttpClient = OkHttpClient()
    private val messageProcessor: MessageProcessor = MessageProcessorImpl()
    private val webSocketListener: WebSocketListener = WebSocketListener(messageProcessor)
    private var webSocket: WebSocket? = null


    fun connect(): Boolean {
        return if (webSocket == null) {
            val request = createRequest()
            webSocket = okHttpClient.newWebSocket(request = request, listener = webSocketListener)
            true
        } else
            false
    }

    fun close() {
        webSocket?.close(1000, "Terminated")
        webSocket = null
        terminate()
    }

    private fun terminate() {
        okHttpClient.dispatcher.executorService.shutdown()
    }

    private fun createRequest(): Request {
        val websocketURL = BuildConfig.WEBSOCKET_URL
        return Request.Builder()
            .url(websocketURL)
            .build()
    }

    fun subscribe(socketComponent: SocketComponent) {
        addMessagePublisher(socketComponent)
        messageProcessor.addComponent(socketComponent)
    }

    private fun addMessagePublisher(socketComponent: SocketComponent) {
        try {
            socketComponent.addPublisher(createAndGetMessagePublisher(socketComponent))
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(socketComponent: SocketComponent) {
        messageProcessor.removeComponent(socketComponent)
    }

    @Throws(IllegalArgumentException::class)
    private fun <T : SocketComponent> createAndGetMessagePublisher(instance: T): MessagePublisher {
        return when (instance) {
//            is ChatSocketComponent -> ChatMessagePublisher(webSocket = webSocket)
            else -> throw IllegalArgumentException("${instance::class.java.name} not found")
        }
    }


}