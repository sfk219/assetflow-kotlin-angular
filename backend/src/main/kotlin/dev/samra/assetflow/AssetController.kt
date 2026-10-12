package dev.samra.assetflow
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = ["http://localhost:4200"])
class AssetController(private val service: AssetService) {
    @GetMapping
    fun list(@RequestParam(required = false) search: String?, @RequestParam(required = false) status: AssetStatus?,
             @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "10") size: Int): Page<Asset> =
        service.list(search, status, page, size)
    @GetMapping("/{id}") fun get(@PathVariable id: String) = service.get(id)
    @GetMapping("/summary") fun summary() = service.summary()
    @PostMapping fun create(@Valid @RequestBody input: AssetRequest): ResponseEntity<Asset> {
        val created = service.create(input)
        return ResponseEntity.created(URI.create("/api/assets/${created.id}")).body(created)
    }
    @PutMapping("/{id}") fun update(@PathVariable id: String, @Valid @RequestBody input: AssetRequest) = service.update(id, input)
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) fun delete(@PathVariable id: String) = service.delete(id)
}
