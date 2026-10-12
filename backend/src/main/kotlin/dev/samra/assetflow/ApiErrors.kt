package dev.samra.assetflow
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant
data class ApiError(val status: Int, val message: String, val timestamp: Instant = Instant.now())
@RestControllerAdvice
class ApiErrors {
    @ExceptionHandler(AssetNotFoundException::class)
    fun notFound(e: AssetNotFoundException) = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError(404, e.message ?: "Not found"))
    @ExceptionHandler(DuplicateKeyException::class)
    fun duplicate(@Suppress("UNUSED_PARAMETER") e: DuplicateKeyException) =
        ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError(409, "Asset serial number already exists"))
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun invalid(e: MethodArgumentNotValidException) = ResponseEntity.badRequest().body(
        ApiError(400, e.bindingResult.fieldErrors.joinToString("; ") { "${it.field}: ${it.defaultMessage}" }))
    @ExceptionHandler(IllegalArgumentException::class, HttpMessageNotReadableException::class)
    fun badRequest(e: Exception) = ResponseEntity.badRequest().body(ApiError(400, e.message ?: "Invalid request"))
}
