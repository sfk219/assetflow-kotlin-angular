# REST API contract
Base URL: http://localhost:8080/api/assets
| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | /api/assets?page=0&size=10&search=laptop&status=AVAILABLE | Search/paginate |
| GET | /api/assets/{id} | Read |
| GET | /api/assets/summary | Status counts |
| POST | /api/assets | Create (201) |
| PUT | /api/assets/{id} | Update |
| DELETE | /api/assets/{id} | Delete (204) |

Example:
```bash
curl -X POST http://localhost:8080/api/assets -H 'Content-Type: application/json' -d '{"name":"Workstation","serialNumber":"DEMO-0001","category":"IT","ownerTeam":"Cloud","status":"AVAILABLE","purchaseValue":950.00}'
```
Demo is for local use and has no authentication.
