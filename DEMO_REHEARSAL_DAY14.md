Demo Rehearsal 1 — Day 14
=========================

Objective
---------
Run a tight 6-minute demo of the Risk Register API + static frontend for the full team with a stopwatch. Record any issues and notes so we can iterate.

Files added for this rehearsal
- `scripts/start_demo.sh` — small helper to start the stack (Docker Compose preferred) or run the backend and frontend locally.

Before the rehearsal (30+ minutes before)
-----------------------------------------
- All presenters join a short pre-call (5 min) to confirm roles and the running order.
- Ensure your machine has:
  - Docker & Docker Compose (recommended) OR a reachable Postgres + Redis for running from source
  - Java 17, Maven (if running backend from source)
  - A modern browser (Chrome/Firefox)
- Open two terminals: one for backend logs, one for demo commands.
- Optional: run screen-recording software and test microphone/camera.

Quick start (recommended - Docker Compose)
-----------------------------------------
1. From repository root:

```bash
# start full stack (Postgres, Redis, backend)
cd backend
./scripts/start_demo.sh docker
```

2. Serve the frontend (in a second terminal):

```bash
cd frontend
python3 -m http.server 8000
# open http://localhost:8000
```

Quick start (no Docker, run from source)
---------------------------------------
1. Ensure a Postgres and Redis instance are reachable and `backend/.env` is configured.
2. Run backend from source:

```bash
cd backend
./scripts/start_demo.sh run
```

3. Serve frontend (same as above):

```bash
cd frontend
python3 -m http.server 8000
```

Timing plan (exact 6:00)
------------------------
Use a stopwatch. Assign a timekeeper to call out time remaining at 1:30 and 0:30 marks.

0:00 — 0:30 (30s) — Opening
- Presenter A: State team, project name and the demo goal (what viewers will see in the next 6 minutes).
- One-line elevator pitch: "A lightweight Spring Boot REST API with JWT auth, email notifications and a small static demo UI — resilient to external email failures."

0:30 — 1:30 (1:00) — Architecture & features (show diagram)
- Presenter B: Brief architecture (point to `README.md` mermaid diagram or show quick slide).
- Mention stack: Java 17, Spring Boot, Postgres, Redis, Thymeleaf emails, Testcontainers (gated), GreenMail test for email.

1:30 — 2:30 (1:00) — Live: Registration (POST /api/auth/register)
- Presenter A: In frontend, open Register form.
- Register a new user (use test2@test.com or sample email).
- Note: Email sending is non-fatal — the API will return 201 even if SMTP is unreachable. If SMTP is configured you may see the email; otherwise you will see a WARN in backend logs.
- Show backend log tail in parallel to demonstrate the mail warning behavior.

2:30 — 3:30 (1:00) — Live: Login -> JWT token
- Presenter B: Use Login form with the account created, obtain token, show how frontend stores token in local state.
- Demonstrate calling a protected endpoint (GET /api/tools) from the UI which uses the token.

3:30 — 4:30 (1:00) — Live: List of tools and Seeder
- Presenter C: Show `/api/tools` listing in UI (there are 30 seeded demo records). Scroll the list to show data and sorting if available.
- Mention DataSeeder created 30 demo tools for the demo.

4:30 — 5:30 (1:00) — Demo reminders/email behavior
- Presenter A: Explain ReminderScheduler and show the unit/integration test that verifies email sending (GreenMail) — open test file `ReminderSchedulerGreenMailTest` or run it locally.
- If SMTP configured: demonstrate sending an email by running a small curl to call a developer-only endpoint (if available) or trigger the scheduler manually from a test. Otherwise show test output and backend log sample demonstrating non-fatal mail failures.

5:30 — 6:00 (0:30) — Wrap & next steps
- Presenter B: Call out what’s done (Day 13 additions — multipart emails, scheduler logic, GreenMail test), and Day 14 goals (this rehearsal notes list).
- Ask for quick questions and say where to file issues or PRs.

Roles & responsibilities during the rehearsal
--------------------------------------------
- Presenter(s): the people demoing flows.
- Timekeeper: calls out "1:30 remaining" and "30s remaining" and stops at 6:00.
- Operator: runs commands, watches logs, and fixes quick infra problems.
- Scribe: records issues into the issue template below.

What to record (Scribe) — issue template
----------------------------------------
Use this template to capture any problem and its context during the rehearsal.

- Time stamp (mm:ss):
- Description: short description of the issue observed
- Repro steps: (commands, which screen)
- Severity: Blocker / High / Medium / Low
- Immediate mitigation (what you did to continue):
- Follow-up owner / ticket:

Example entry:
- 01:42 — "Registration returned 500"
- Repro: clicked Register in frontend -> saw 500 in browser and stack in backend logs
- Severity: High
- Mitigation: Operator caught exception; found UnknownHostException for SMTP host; updated `MAIL_HOST` to valid test SMTP and retried; registration succeeded.
- Follow-up: Create README note to explain MAIL_HOST default and GreenMail usage (owner: @uday)

Common issues & quick fixes
--------------------------
- Docker not running / Testcontainers failing
  - Symptom: Integration tests or docker-compose fail with "Could not find a valid Docker environment" or `/var/run/docker.sock` errors.
  - Fix: Start Docker Desktop / ensure Docker daemon running. For CI, run tests without `-Pintegration`.

- SMTP UnknownHost / Connection refused
  - Symptom: MailSendException / UnknownHostException -> backend returns 500 before fixes (older versions)
  - Fix: We already made email non-fatal. For meaningful demo, use a test SMTP server (GreenMail) or configure real SMTP in `backend/.env`.

- Port conflicts (8080, 5432, 8000)
  - Symptom: docker-compose or Spring Boot fails on port bind
  - Fix: stop conflicting process or change `SERVER_PORT` / docker-compose ports temporarily.

- Missing .env values
  - Symptom: app starts but cannot connect to DB or mail
  - Fix: copy `backend/.env.example` -> edit values, or set env variables in terminal before starting.

- JWT / Authorization denied
  - Symptom: 401 / 403 on protected endpoints
  - Fix: ensure you called `/api/auth/login`, received token, and the frontend sends `Authorization: Bearer <token>` header. The demo UI handles this.

Tips to reduce flakiness
-----------------------
- Use GreenMail locally to simulate SMTP during the demo (we added a test that demonstrates how we wire it up).
- Start Docker and the stack at least 2–3 minutes before the demo to allow Flyway migrations and seeding to finish.
- Keep one terminal running `tail -f backend/logs/*.log` or `docker-compose logs -f` so you can quickly show logs for troubleshooting.
- Use incognito/private window to avoid cached auth tokens interfering between runs.
- Keep a list of pre-created demo accounts and demo passwords in a private slide (not included in recording).

Post-rehearsal (immediately after the 6-min run)
------------------------------------------------
- Scribe files the issues captured into GitHub issues or a shared doc; tag owner and priority.
- Team does a 5–10 minute retro: what went well, what didn’t, who will fix the top 3 issues.

Day 14 goals (after this rehearsal)
----------------------------------
- Fix any high-severity blockers uncovered during the rehearsal (e.g., add `backend/.env.example` coverage, make reminder recipients configurable).
- Add a brief CI job that runs unit tests and the GreenMail-based email test (no Docker required) so future rehearsals are faster to verify.
- Polish frontend flows and add an explicit "Trigger reminder" dev endpoint behind authentication for demoers.

Contacts / owners
- Demo lead: @uday
- Backend owner: @sowkya261
- Frontend owner: (you)

Good luck — tell me if you want me to also:
- Create a small GitHub Actions workflow that runs the GreenMail test on push (Day 14 item), or
- Add `backend/.env.example` and a Docker Compose snippet for quick local start, or
- Prepare a single-slide PDF with the 6-minute script for the team to follow.

