# Risk Register API (Public REST)

A small demo project: a Spring Boot-based REST API for tracking risks / tools in an internship-style sample app, with a tiny static frontend for demoing common flows (register, login, list items). This repository contains the backend service (Java / Spring Boot), a static demo frontend, and test scaffolding (unit tests + optional integration tests).

## Highlights
...
## Architecture

```mermaid
flowchart LR
	subgraph Frontend
		F[Static SPA (frontend/index.html)]
	end

	subgraph Backend
		B[Spring Boot REST API]
		Auth[(JWT Auth)]
		Mail[(SMTP / JavaMail)]
		Scheduler[(ReminderScheduler)]
	end

	subgraph Infra
		P[(Postgres)]
		R[(Redis)]
	end

	F -->|HTTP JSON| B
	B -->|JDBC| P
	B -->|Cache| R
	B -->|SMTP| Mail
	B --> Scheduler

	note right of P: Flyway migrations applied on startup
	note left of Mail: configured via env vars (MAIL_HOST, MAIL_PORT...)
```

## Prerequisites
...
## Quick setup (local development)

1. Clone the repo

```bash
git clone git@github.com:sowkya261/risk-register-api-public-rest.git
cd risk-register-api-public-rest
```

2. Backend configuration

- Copy the example environment template (create `.env` in the `backend` directory or provide env vars another way):

```bash
cp backend/.env.example backend/.env
# Edit backend/.env to match your environment (DB credentials, mail server, etc.)
```

- A minimal set of values to run the backend using a local Postgres / Redis (via Docker Compose) or to connect to your own Postgres instance is listed in the `.env` reference below.

3. Start the local stack with Docker Compose (recommended)

```bash
cd backend
docker-compose up -d --build
```

After compose starts, the backend will be available on http://localhost:8080 by default.

4. Run the backend from source (no Docker required if you have a DB/Redis available)

```bash
# from the backend directory
mvn spring-boot:run
```

5. Run unit tests

```bash
mvn -DskipTests=false test
```

6. Run integration tests (optional — requires Docker)

Integration tests use Testcontainers and are gated behind the `integration` profile. Run them only on a Docker-capable machine:

```bash
mvn -Pintegration verify
```

## Frontend (demo)

The static demo frontend is in the `frontend/` directory. It is a single-page demo that calls the backend API for register/login/list flows.

Start a simple static server from the `frontend` directory. Example using Python:

```bash
cd frontend
python3 -m http.server 8000
# then open http://localhost:8000 in your browser
```

Or use a Node static server (npm package `serve`):

```bash
cd frontend
npx serve .
```

## .env reference (backend)

Create a file at `backend/.env` (or set equivalent env vars). Below is a reference table of supported environment variables and recommended defaults for local testing.

Note: variables may be prefixed or picked up by Spring Boot `application.properties` — the backend reads the following common env vars:

| Variable | Example / Default | Purpose |
|---|---:|---|
| DB_HOST | localhost | Postgres hostname |
| DB_PORT | 5432 | Postgres port |
| DB_NAME | riskdb | Postgres database name |
| DB_USER | postgres | Postgres username |
| DB_PASSWORD | postgres | Postgres password |
| REDIS_HOST | localhost | Redis hostname |
| REDIS_PORT | 6379 | Redis port |
| MAIL_HOST | smtp.example.com | SMTP host used by JavaMailSender |
| MAIL_PORT | 587 | SMTP port |
| MAIL_USERNAME | user@example.com | SMTP auth user (optional) |
| MAIL_PASSWORD | secret | SMTP auth password (optional) |
| MAIL_FROM | noreply@example.com | From address for outgoing mail |
| JWT_SECRET | change-me | Secret used to sign JWTs (set a strong secret in prod) |
| SPRING_PROFILES_ACTIVE | dev | Spring profile(s) to activate |
| SERVER_PORT | 8080 | Port the Spring Boot app listens on |

For Docker Compose the project includes an example `backend/.env.example` (if present) you can copy and adapt.

## Common endpoints
- POST /api/auth/register — register a user (returns 201 on success). Email sending is non-fatal: failures are logged and registration still completes.
- POST /api/auth/login — obtain JWT token
- GET /api/tools — list seeded tools (requires token)

See the server logs for warnings related to email delivery if you haven't configured a working SMTP server.

## Testing notes
- Unit tests (Surefire) run by default with `mvn test` and are safe to run on machines without Docker.
- Integration tests using Testcontainers are intentionally gated behind the Maven profile `integration` and require Docker available on the host. To run them use `mvn -Pintegration verify`.

If you want to validate email sending during automated tests without Docker/Testcontainers, consider running the in-memory SMTP test server (GreenMail) locally — this is proposed in the Day 13 plan below.

## Day 13 — Next steps and roadmap

Planned work for Day 13 (short checklist):

- Implement ReminderScheduler logic to select items with upcoming deadlines and send reminder emails using the existing Thymeleaf templates.
- Produce multipart messages that include both HTML and plaintext parts in the same email (improves deliverability and accessibility).
- Add an in-process SMTP test server (GreenMail) based integration test so CI can validate email content without requiring external SMTP or Docker.
- Move Testcontainers integration tests to a separate module or ensure they are strictly gated by `-Pintegration` so default CI builds remain fast and Docker-free.
- Add a small CI job that runs unit tests, lints, and the GreenMail-based email test; make the Docker/Testcontainers job optional or run on a self-hosted runner with Docker.

If you'd like, I can implement the ReminderScheduler email logic and add a GreenMail-based integration test next — tell me which of those you'd prefer me to tackle first.

## Contributing
- Fork, create a branch, and open a PR against `java-dev1` (this branch). Keep changes small and focused. Add tests for new behavior.

## License
This repository is provided as-is for demo and learning purposes. There is no formal license attached — add one if you plan to publish or reuse this code publicly.

---

Last updated: Day 13 (planned work described). If anything in this README is out of date for your workspace layout, let me know and I will update it.

# Risk Register API (Public REST)