package dev.samra.assetflow
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.Optional
class AssetServiceTest {
    private val repository = mock(AssetRepository::class.java)
    private val service = AssetService(repository)
    private val sample = Asset(id = "abc", name = "Laptop", serialNumber = "SN-1", category = "IT", ownerTeam = "Platform")
    @Test fun `retrieves asset by id`() {
        `when`(repository.findById("abc")).thenReturn(Optional.of(sample))
        assertEquals("Laptop", service.get("abc").name)
    }
    @Test fun `missing asset produces specific exception`() {
        `when`(repository.findById("unknown")).thenReturn(Optional.empty())
        assertThrows(AssetNotFoundException::class.java) { service.get("unknown") }
    }
    @Test fun `rejects invalid page size`() {
        assertThrows(IllegalArgumentException::class.java) { service.list(null, null, 0, 101) }
    }
    @Test fun `supports filtering by asset status`() {
        `when`(repository.findByStatus(eq(AssetStatus.AVAILABLE), any(Pageable::class.java)))
            .thenReturn(PageImpl(listOf(sample)))
        assertEquals(1, service.list(null, AssetStatus.AVAILABLE, 0, 10).totalElements)
    }
}
