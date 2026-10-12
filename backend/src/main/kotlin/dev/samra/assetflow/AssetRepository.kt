package dev.samra.assetflow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
interface AssetRepository : MongoRepository<Asset, String> {
    fun findByNameContainingIgnoreCase(name: String, pageable: Pageable): Page<Asset>
    fun findByStatus(status: AssetStatus, pageable: Pageable): Page<Asset>
    fun findByStatusAndNameContainingIgnoreCase(status: AssetStatus, name: String, pageable: Pageable): Page<Asset>
    fun countByStatus(status: AssetStatus): Long
}
