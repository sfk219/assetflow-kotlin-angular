package dev.samra.assetflow
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Instant
class AssetNotFoundException(id: String) : RuntimeException("Asset '$id' not found")
@Service
class AssetService(private val repository: AssetRepository) {
    fun list(search: String?, status: AssetStatus?, page: Int, size: Int): Page<Asset> {
        require(page >= 0 && size in 1..100) { "page must be nonnegative and size between 1 and 100" }
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        val term = search?.trim()?.takeIf { it.isNotEmpty() }
        return when {
            status != null && term != null -> repository.findByStatusAndNameContainingIgnoreCase(status, term, pageable)
            status != null -> repository.findByStatus(status, pageable)
            term != null -> repository.findByNameContainingIgnoreCase(term, pageable)
            else -> repository.findAll(pageable)
        }
    }
    fun get(id: String): Asset = repository.findById(id).orElseThrow { AssetNotFoundException(id) }
    fun create(input: AssetRequest): Asset = repository.save(
        Asset(name = input.name.trim(), serialNumber = input.serialNumber.trim(), category = input.category.trim(),
            ownerTeam = input.ownerTeam.trim(), status = input.status, purchaseValue = input.purchaseValue)
    )
    fun update(id: String, input: AssetRequest): Asset {
        val current = get(id)
        return repository.save(current.copy(
            name = input.name.trim(), serialNumber = input.serialNumber.trim(), category = input.category.trim(),
            ownerTeam = input.ownerTeam.trim(), status = input.status, purchaseValue = input.purchaseValue,
            updatedAt = Instant.now()))
    }
    fun delete(id: String) { repository.delete(get(id)) }
    fun summary() = AssetSummary(repository.count(), AssetStatus.entries.associateWith(repository::countByStatus))
}
