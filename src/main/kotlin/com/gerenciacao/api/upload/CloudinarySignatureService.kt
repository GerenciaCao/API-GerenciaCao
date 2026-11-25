package com.gerenciacao.api.upload

import org.springframework.stereotype.Service
import java.security.MessageDigest

/**
 * Dados retornados ao app Android para ele fazer o upload multipart direto ao Cloudinary.
 * O apiSecret nunca sai do backend.
 */
data class CloudinarySignatureResponse(
    val cloudName: String,
    val apiKey: String,
    val timestamp: Long,
    val signature: String,
    val folder: String,
    val uploadUrl: String,
)

@Service
class CloudinarySignatureService(
    private val properties: CloudinaryProperties,
) {

    fun generateSignature(rawFolder: String?): CloudinarySignatureResponse {
        require(properties.cloudName.isNotBlank()) { "CLOUDINARY_CLOUD_NAME não configurado." }
        require(properties.apiKey.isNotBlank()) { "CLOUDINARY_API_KEY não configurado." }
        require(properties.apiSecret.isNotBlank()) { "CLOUDINARY_API_SECRET não configurado." }

        // Padroniza a pasta (ex.: "gerenciacao/animais") e remove caracteres perigosos
        val folder = (rawFolder ?: "gerenciacao/animais")
            .trim()
            .replace(Regex("[^a-zA-Z0-9/_-]"), "")
            .ifBlank { "gerenciacao/animais" }

        // Cloudinary exige o timestamp em segundos (Unix epoch)
        val timestamp = System.currentTimeMillis() / 1000L

        // Parâmetros assinados devem estar em ordem alfabética e separados por '&'
        val params = sortedMapOf(
            "folder" to folder,
            "timestamp" to timestamp.toString(),
        )
        val stringToSign = params.entries.joinToString("&") { "${it.key}=${it.value}" }

        // Regra oficial: <parametros_ordenados><api_secret> -> SHA-1 em hex minúsculo
        val signature = sha1Hex("$stringToSign${properties.apiSecret}")

        return CloudinarySignatureResponse(
            cloudName = properties.cloudName,
            apiKey = properties.apiKey,
            timestamp = timestamp,
            signature = signature,
            folder = folder,
            uploadUrl = "https://api.cloudinary.com/v1_1/${properties.cloudName}/image/upload",
        )
    }

    private fun sha1Hex(value: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val bytes = digest.digest(value.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
