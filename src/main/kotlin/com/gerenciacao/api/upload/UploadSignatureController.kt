package com.gerenciacao.api.upload

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/uploads")
class UploadSignatureController(
    private val signatureService: CloudinarySignatureService,
) {

    /**
     * Exemplo:
     * GET https://genciacao-docker.onrender.com/api/uploads/cloudinary-signature?folder=gerenciacao/animais
     */
    @GetMapping("/cloudinary-signature")
    fun getCloudinarySignature(
        @RequestParam(required = false, defaultValue = "gerenciacao/animais") folder: String,
    ): ResponseEntity<CloudinarySignatureResponse> {
        return ResponseEntity.ok(signatureService.generateSignature(folder))
    }
}
