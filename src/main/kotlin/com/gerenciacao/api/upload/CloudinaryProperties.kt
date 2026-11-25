package com.gerenciacao.api.upload

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Lê as credenciais do Cloudinary definidas nas variáveis de ambiente do Render
 * (mapeadas em gerenciacao.cloudinary.* no application.yml).
 */
@ConfigurationProperties(prefix = "gerenciacao.cloudinary")
data class CloudinaryProperties(
    val cloudName: String,
    val apiKey: String,
    val apiSecret: String,
)
