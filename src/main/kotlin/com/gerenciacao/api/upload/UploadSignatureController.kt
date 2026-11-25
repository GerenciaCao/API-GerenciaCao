package com.gerenciacao.api.upload

import com.gerenciacao.api.domain.Permissao
import com.gerenciacao.api.security.UsuarioAutenticado
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/uploads")
class UploadSignatureController(
    private val signatureService: CloudinarySignatureService,
) {

    // Exemplo:
    // GET https://genciacao-docker.onrender.com/api/uploads/cloudinary-signature?folder=gerenciacao/animais
    // Header: Authorization: Bearer <SEU_JWT>
    @GetMapping("/cloudinary-signature")
    fun getCloudinarySignature(
        @AuthenticationPrincipal eu: UsuarioAutenticado,
        @RequestParam(required = false, defaultValue = "gerenciacao/animais") folder: String,
    ): ResponseEntity<CloudinarySignatureResponse> {
        eu.exigir(Permissao.EDITAR_PRONTUARIOS)
        return ResponseEntity.ok(signatureService.generateSignature(folder))
    }
}
