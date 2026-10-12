package dev.samra.assetflow
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant
enum class AssetStatus { AVAILABLE, ASSIGNED, MAINTENANCE, RETIRED }
@Document("assets")
data class Asset(
    @Id val id: String? = null,
    val name: String,
    @Indexed(unique = true) val serialNumber: String,
    val category: String,
    val ownerTeam: String,
    val status: AssetStatus = AssetStatus.AVAILABLE,
    val purchaseValue: BigDecimal = BigDecimal.ZERO,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
data class AssetRequest(
    @field:NotBlank @field:Size(max = 120) val name: String,
    @field:NotBlank @field:Size(max = 64) val serialNumber: String,
    @field:NotBlank @field:Size(max = 60) val category: String,
    @field:NotBlank @field:Size(max = 60) val ownerTeam: String,
    val status: AssetStatus = AssetStatus.AVAILABLE,
    @field:DecimalMin("0.00") val purchaseValue: BigDecimal = BigDecimal.ZERO
)
data class AssetSummary(val total: Long, val byStatus: Map<AssetStatus, Long>)
