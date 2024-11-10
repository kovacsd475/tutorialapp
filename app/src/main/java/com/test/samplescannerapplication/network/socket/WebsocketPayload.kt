package com.test.samplescannerapplication.network.socket

import com.squareup.moshi.Json

/**
 * Created by kovacsdavid on 06,november,2024
 *
 */
data class WebsocketPayload(
    @field:Json(name = "payload_type")
    val payloadType: String,
    val payload: Payload,
)

data class Payload(val event: String, val message: WebsocketMessage)

interface WebsocketMessage