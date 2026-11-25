package com.gerenciacao.api

import org.springframework.boot.context.properties.ConfigurationProperties

// Mapeia as chaves "cloudinary.*" do application.yml para um objeto tipado
@ConfigurationProperties(prefix = "cloudinary")
data class CloudinaryProperties(
    val cloudName: String,
    val apiKey: String,
    val apiSecret: String
)
