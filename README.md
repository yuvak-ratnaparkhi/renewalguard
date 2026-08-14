\# RenewalGuard



\*\*A state-machine-driven contract and license expiry escalation management system.\*\*



Track SSL certificates, software licenses, contracts, insurance policies, and domains with automatic multi-stage reminder escalation and renewal history auditing.



\## Problem Solved

Small businesses miss renewal deadlines (SSL certs expire, licenses lapse, contracts auto-renew) because there's no centralized tracking. This system automatically notifies relevant stakeholders in stages and prevents costly mishaps.



\## Tech Stack

\- \*\*Backend:\*\* Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA, Hibernate

\- \*\*Database:\*\* MySQL

\- \*\*Frontend:\*\* React (or HTML/CSS/JS)

\- \*\*Deployment:\*\* Render (backend), Railway/Render (MySQL), Vercel (frontend)

\- \*\*Testing:\*\* JUnit 5, Mockito

\- \*\*Documentation:\*\* Swagger/OpenAPI, Postman



\## Key Features

\- Asset registry (SSL certs, licenses, contracts, domains, insurance, etc.)

\- Configurable multi-stage escalation policies

\- Automatic daily state transitions (ACTIVE → NEARING\_EXPIRY → ESCALATED → EXPIRED)

\- Notification logging on every lifecycle event

\- Renewal history tracking with audit trail

\- Role-based access control (ADMIN, OWNER)

\- JWT authentication

\- RESTful API with Swagger documentation



\## Development Phases

\- \*\*Phase 0:\*\* Setup \& Planning

\- \*\*Phase 1:\*\* Foundation (entities, auth)

\- \*\*Phase 2:\*\* Core domain logic (state machine, CRUD)

\- \*\*Phase 3:\*\* Automation (scheduler, notifications)

\- \*\*Phase 4:\*\* Frontend \& API docs

\- \*\*Phase 5:\*\* Testing, polish, deployment



\## Getting Started (WIP)

Setup and build instructions coming soon.



\## License

MIT



