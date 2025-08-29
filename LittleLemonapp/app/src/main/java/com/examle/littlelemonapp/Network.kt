package com.examle.littlelemonapp

import kotlinx.serialization.Serializable
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

