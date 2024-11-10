package com.test.samplescannerapplication.network.socket

/**
 * Created by kovacsdavid on 06,november,2024
 * */
object SocketComponentFactory {
    @Throws(IllegalArgumentException::class)
    fun <T : Any> get(clazz: Class<T>, messageReceiver: MessageReceiver): SocketComponent {
        return when (clazz) {
//            ChatSocketComponent::class.java -> ChatSocketComponentImpl(messageReceiver as ChatMessageReceiver)
            else -> {
                throw IllegalArgumentException("${clazz.name} socket component not found, $messageReceiver not found")
            }
        }
    }
}