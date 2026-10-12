# AssetFlow · Kotlin + Spring Boot + Angular + MongoDB

A full-stack **asset lifecycle management** portfolio application. Independent code based on fictional data; not derived from employer/client source or records.

## Features
- Kotlin/Spring Boot REST API backed by MongoDB
- Asset creation, update, deletion and search with pagination, status filters and unique serial numbers
- Angular/TypeScript dashboard with form validation, statistics and a responsive inventory table
- Backend service tests, GitHub Actions CI, and Docker Compose configuration

## Architecture
```
Angular web UI (:4200) -> Spring Boot (:8080) -> MongoDB (:27017)
```

## Quick start (requires Docker Engine/Desktop and Compose)
```bash
docker compose up --build
```
Open http://localhost:4200. Alternatively, start MongoDB and run `mvn spring-boot:run` in `backend/` and `npm install && npm start` in `frontend/`.

## Tests
```bash
cd backend && mvn test
cd ../frontend && npm install && npm run build
```
See [API reference](docs/API.md) for request examples.

## Scope
Demo only. No authentication, audit trail or production security controls; run locally. Build/deployment and browser end-to-end operation have not been verified in this environment.
