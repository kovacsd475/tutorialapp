package com.test.samplescannerapplication.network.socket

import okio.ByteString

/**
 * Created by kovacsdavid on 06,november,2024
 */
interface MessageProcessor {
    fun addComponent(socketComponent: SocketComponent)
    fun removeComponent(socketComponent: SocketComponent)
    fun onMessage(bytes: ByteString)
    fun onMessage(text: String)
}