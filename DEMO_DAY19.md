# Day 19 — Demo: Dashboard KPIs, Search/Filter, CSV Export, Audit Log, Email Notifications, Responsive View

Goal
- Deliver a small-but-polished dashboard that showcases key product metrics, fast search/filtering, CSV export, an audit log for actions, live email notification, and a responsive UI for the demo.

Summary of features to present
1. Dashboard KPIs (cards): total tools, tools added in last 7 days, active users, pending reminders.
2. Search & filter: full-text search + simple filters (tag, owner, status) with debounce and keyboard focus.
3. CSV export: export search results and current filtered list as CSV (server-side endpoint or client-side transform for small sets).
4. Audit log: table of recent actions (user, action, target, timestamp, metadata) with a simple API and frontend viewer.
5. Email notification: demo a live notification sent to admin when a new tool is created (uses existing EmailService with async multipart sending and resilient fallback).
6. Responsive view: small-screen layout demonstration (mobile width) to show the UI adapts.

Acceptance criteria (demo-ready)
- Dashboard shows 4 KPI cards with numbers and a short tooltip/explain link.
- Search box returns results quickly and shows a loading indicator; filters are additive and persist during CSV export.
- Export button produces a CSV file named `tools-YYYYMMDD.csv` containing the currently visible list.
- Audit log endpoint returns the last 100 actions; UI shows last 10 with paging controls.
- When a new tool is created (via API or frontend form), an email is sent to `admin@localhost` and the demo logs show the send action happened (or GreenMail shows the mail during tests). Failure to send is logged but doesn't break the create flow.
- All UI screens render clearly at 375x812 (iPhone X) and 1920x1080; screenshots for both sizes are included in the demo bundle.

Minimal API contracts
- GET /api/dashboard/kpis
  - Response: { totalTools: number, toolsLast7Days: number, activeUsers: number, pendingReminders: number }
- GET /api/tools?search=&tag=&owner=&status=&page=&size=
  - Response: pageable Tools list (existing) — ensure server supports `search` param (full-text) and filters.
- GET /api/tools/export?format=csv&search=&tag=...
  - Response: Content-Type: text/csv; attachment; body is CSV of fields: id,title,owner,createdAt,status,tags
- GET /api/audit?limit=100&page=0
  - Response: [{id, user, action, targetId, targetType, metadata, createdAt}] (new `audit` table or collection)
- POST /api/tools
  - Existing create endpoint — after create, server triggers `emailService.sendMultipartEmail(adminEmail, subject, text, html)` (async, non-blocking). Also log an audit entry: { user, action: 'CREATE_TOOL', targetId, metadata }

Data shape notes
- AuditEntry entity: id (UUID), userId (or username), action (enum), targetId (UUID), targetType (string), metadata (JSON), createdAt (timestamp).
- For CSV export, only include fields useful to reviewers; don't leak secrets.

Frontend UI notes
- Dashboard (top): 4 KPI cards (grid) + refresh button.
- Middle: Search bar + filter pills + export button (right aligned).
- Below: Tools list (table) with columns: title, owner, createdAt, status, actions (view/edit).
- Right/overlay: small Audit log drawer showing last 10 actions with a time relative (2m ago).
- Responsive: KPI cards stack vertically; search & export collapse into a single toolbar; table becomes a list with summary rows on mobile.

UX details and demo script (1–2 minutes)
- Start at 1920x1080: show KPI cards updating after seed run (or refresh): highlight the number of tools added in last 7 days.
- Type a search term (example 'calendar') and show results filtering and loading indicator; toggle a tag filter and show combined filtering.
- Click Export CSV — open the CSV in a text editor briefly or show contents in the terminal to validate headers.
- Create a new tool via the frontend or a quick curl POST — show success toast and a log entry in the audit drawer; then show that the admin receives a notification (or that GreenMail captured it in tests). If SMTP is not configured show the resilient log message.
- Shrink to mobile width and show the responsive layout for KPI cards and tools list.

Implementation roadmap (timeboxed for a single day)
- 0.5h: API stubs and DB migration for `audit` table; add service and repository.
- 0.5h: KPI backend endpoint aggregating counts (SQL or repository queries).
- 1.0h: Tools search param support (simple LIKE or Postgres full-text if already used). Add `search` handling in `ToolServiceImpl`.
- 1.0h: CSV export endpoint (reuse the `findAll`/search logic and stream CSV); ensure Content-Disposition header.
- 1.0h: Email hook on create (invoke existing `EmailService.sendMultipartEmail` with safe try/catch). Also create audit entry on create.
- 1.5h: Frontend UI: dashboard cards, search bar + filter UI, export button, audit drawer, and responsive CSS. Use existing `frontend/app.js` or create a small `dashboard.html` page.
- 0.5h: Smoke tests and demo run-through, capture mobile & desktop screenshots with the `tools/screenshot` script.

Testing and verification
- Unit tests for KPI aggregation, CSV export service, and audit repository.
- Integration test: use GreenMail to assert that creating a tool triggers an email (async) and an audit entry is stored.
- Manual smoke: run backend + frontend, seed data (30 tools), perform flows in the UI, and capture screenshots.

Edge cases & security
- CSV export should be rate-limited or limited to authorized users — for demo, restrict to admin role or internal access.
- Audit content should not contain PII; if metadata includes sensitive info, sanitize or truncate.
- Email sending must be async and tolerant of failures (already implemented in earlier days); ensure it doesn't block the create flow.

Deliverables for the demo
- New endpoints: `/api/dashboard/kpis`, `/api/tools?search=...`, `/api/tools/export?format=csv`, `/api/audit` and audit DB migration.
- Frontend: `frontend/dashboard.html`, `frontend/dashboard.js`, `frontend/dashboard.css` or additions to `index.html` to show the dashboard and audit drawer.
- Tests: unit tests for KPI and CSV export; GreenMail integration test for email-on-create.
- Screenshots: 1920x1080 and 375x812 views for the key flows.

Quick implementation hints (code snippets)
- CSV export (Spring Boot controller):
  - Set `response.setContentType("text/csv"); response.setHeader("Content-Disposition","attachment; filename=tools-"+ LocalDate.now()+".csv");` and write CSV lines using a streaming writer.
- Audit entry creation example in service after tool saved:
  - `auditService.log(user.getUsername(), "CREATE_TOOL", tool.getId(), Map.of("title", tool.getTitle()));`
- KPI query (JPQL or native):
  - `select count(t) from Tool t` and `select count(t) from Tool t where t.createdAt >= :sevenDaysAgo`

Next actions I can take now
- Option A (fast): create `DEMO_DAY19.md` in the repo with the above content and push it so the team can start work.
- Option B (hands-on): scaffold minimal backend code for KPIs, CSV export endpoint, and audit table migration (I can create small, safe stubs and tests); requires editing Java files and running `mvn test` afterwards.

Which do you want me to do now? If you want Option B, tell me whether to scaffold just the APIs and tests, or to include frontend scaffolding too.