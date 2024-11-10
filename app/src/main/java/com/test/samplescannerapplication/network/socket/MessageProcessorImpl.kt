package com.test.samplescannerapplication.network.socket

import okio.ByteString

/**
 * Created by kovacsdavid on 06,november,2024
 * */
class MessageProcessorImpl : MessageProcessor {
    private val listSocketComponent: Set<SocketComponent> = mutableSetOf()

    override fun addComponent(socketComponent: SocketComponent) {
        (listSocketComponent as MutableSet<SocketComponent>).add(socketComponent)
    }

    override fun removeComponent(socketComponent: SocketComponent) {
        (listSocketComponent as MutableSet<SocketComponent>).remove(socketComponent)
    }

    private fun getComponent(index: Int): SocketComponent {
        return listSocketComponent.elementAt(index)
    }

    private fun <T : SocketComponent> getComponents(clazz: Class<T>): List<T> {
        return listSocketComponent.filterIsInstance(clazz)
    }


    /**
     *
     */
    override fun onMessage(bytes: ByteString) {
    }

    override fun onMessage(text: String) {
    }
}