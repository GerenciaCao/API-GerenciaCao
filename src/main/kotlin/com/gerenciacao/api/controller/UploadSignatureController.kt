package com.gerenciacao.api.upload

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/uploads")
class UploadSignatureController(
    private val signatureService: CloudinarySignatureService
) {

    // Exemplo de chamada: GET /api/uploads/cloudinary-signature?folder=pets
    @GetMapping("/cloudinary-signature")
    fun getSignature(
        @RequestParam(required = false, defaultValue = "pets") folder: String
    ): ResponseEntity<CloudinarySignatureResponse> {
        val response = signatureService.generateUploadSignature(folder)
        return ResponseEntity.ok(response)
    }
}
