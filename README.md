# RenewalGuard

**A state-machine-driven contract and license expiry escalation management system.**

Track SSL certificates, software licenses, contracts, insurance policies, and domains with automatic multi-stage reminder escalation and renewal history auditing.

---

## Problem Solved

Small businesses miss renewal deadlines — SSL certs expire, licenses lapse, contracts auto-renew — because there's no centralized tracking. RenewalGuard automatically escalates alerts to the right stakeholders in stages, preventing costly mishaps.

---

## Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA, Hibernate |
| **Database** | MySQL |
| **Frontend** | React (planned) |
| **Deployment** | Render / Railway (backend + MySQL), Vercel (frontend) |
| **Testing** | JUnit 5, Mockito |

---

## Core Features

- **Asset Management** — full CRUD for tracked assets (SSL certs, licenses, contracts, domains, insurance)
- **Escalation Policies** — configurable multi-step alert rules (e.g. 30/7/1 days before expiry)
- **EscalationEngine** — state machine that evaluates asset status against policy steps automatically
- **Role-Based Access Control** — `ADMIN` sees everything, `OWNER` sees only their own assets
- **JWT Authentication** — stateless, secure API access
- **Asset Renewal** — reset expiry and status with a single call

---

## Project Status

| Phase | Description | Status |
|---|---|---|
| **Phase 1** | Foundation — entities, repositories, JWT security, auth | ✅ Complete |
| **Phase 2** | Core domain logic — Asset/Policy CRUD, EscalationEngine, RBAC | ✅ Complete |
| **Phase 3** | Scheduled jobs, notification logging, renewal history, audit trail | 🔜 Next |

---

## API Overview

### Auth
| Method | Endpoint | Access |
|---|---|---|
| POST | `/auth/register` | Public |
| POST | `/auth/login` | Public |

### Assets
| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/v1/assets` | OWNER, ADMIN |
| GET | `/api/v1/assets` | OWNER (own only), ADMIN (all) |
| GET | `/api/v1/assets/{id}` | OWNER (own only), ADMIN |
| GET | `/api/v1/assets/expiring?days=N` | OWNER, ADMIN |
| PUT | `/api/v1/assets/{id}` | OWNER (own only), ADMIN |
| POST | `/api/v1/assets/{id}/renew` | OWNER (own only), ADMIN |
| DELETE | `/api/v1/assets/{id}` | ADMIN only |

### Escalation Policies
| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/v1/policies` | ADMIN only |
| GET | `/api/v1/policies` | OWNER, ADMIN |
| GET | `/api/v1/policies/{id}` | OWNER, ADMIN |
| PUT | `/api/v1/policies/{id}` | ADMIN only |
| DELETE | `/api/v1/policies/{id}` | ADMIN only |

---

## Getting Started

### Prerequisites
- Java 17+
- MySQL 8+
- Maven

### Setup

```bash
git clone https://github.com/yuvak-ratnaparkhi/renewalguard.git
cd renewalguard/backend
```

Create a database:
```sql
CREATE DATABASE renewalguard_db;
```

Set environment variables (or configure in your IDE run config):
```
DB_USERNAME=root
DB_PASSWORD=<your-mysql-password>
JWT_SECRET=<your-random-secret>
```

Run the app:
```bash
mvn spring-boot:run
```

Server starts on `http://localhost:8081`.

---

## License

This project is for educational/portfolio purposes.
