# Day 18 — Final Confidence Check: 90-second solo sections & gap resolution

Objective
- Ensure every team member can deliver a focused 90-second section solo.
- Identify and resolve all remaining gaps, blockers, or unclear points during Day 18 so the team is demo-ready.

Why this matters
- Short, tightly rehearsed solo sections prove individual ownership and help avoid verbal collisions during the live demo.
- A focused gap-resolution day ensures no last-minute surprises (broken demo pages, flaky API calls, missing env vars, or unclear answers).

Overview (one-liner)
- Each team member delivers a 90-second prepared solo section. After everyone finishes, run a 30–45 minute triage to resolve critical gaps, assign owners, and set short follow-up tasks for the final polish.

Before Day 18
- Prepare a single slide with per-person 90s time and the 3–5 bullet points each will cover (only bullets; no scripts).
- Confirm the demo environment (backend up, DB seeded, env file in place). If the demo relies on Docker Compose, start it and confirm health endpoints.
- Ensure one engineer can watch logs and quickly restart services if needed.

Run format
- Total baseline time: depends on team size; plan for 90s per person + transitions + triage.
- Example for N members: N * 90s + (N * 10s transitions) + 30–45 minutes triage.

Moderator script (concise)
- "We’ll do 90 seconds per person — no slides, no notes in hand. Speak to your three bullets. Time starts on my cue. 10s transition between speakers. After all solos, we’ll run a focused triage for up to 45 minutes to fix blocking gaps." 

What each speaker should cover (tight 90s)
- 10s: One-line elevator summary of your area/contribution.
- 40s: One technical choice or implementation highlight you own + why it matters.
- 25s: One concrete demo or test that shows it works (link a file/route, exact command, or test name).
- 15s: One remaining issue you'd like the team to address today (if any) or a follow-up ask.

Timekeeping rules
- Timekeeper gives a 30s and 10s warning. At 90s the moderator stops the speaker.
- If a person finishes early, the moderator can allow a single 10s extension if others agree.

Scoring and quick feedback
- For each speaker, peers score 0–2 on: clarity (what it is), ownership (did they demonstrate ownership), impact (did they show a result). Total max: 6.
- After each speaker, allow a single 15s clarifying question from the team (optional).

Gap triage (30–45 minutes)
- Immediately after the solos, open a shared board (GitHub Issues, Trello, Miro) and capture every gap raised during the solos and any live failures seen during short verification runs.
- Classify each item as: Blocker (must fix before demo), Important (fix if time), Nice-to-have.
- Assign an owner and a fixed ETA (e.g., 30m, 2h) inside the triage.
- Owners must create small PRs or branches and test locally; the Moderator/Tech lead verifies fixes and closes the item.

Example triage flows
- Demo page 404 on the VM: Owner reproduces locally; if due to wrong base URL, update config and redeploy; verify with a simple curl and the screenshot script.
- Authentication issue: create a quick postman collection or curl snippet the moderator can run to confirm tokens are issued and accepted.
- Broken seed data: run the seeder script (or provide a prepared SQL) and confirm tools list page loads with seeded data.

Escalation & rollback plan
- If a hard blocker can't be resolved within the allotted time, prepare a rollback plan: a recorded demo clip of the failing piece or a saved set of screenshots to present instead.
- Keep a clean branch/tag from which to create the recording if needed.

Deliverables by end of Day 18
- All Blockers resolved or deferred to a documented rollback plan.
- A single summary doc: list of fixed items, owners, and any remaining Important/Nice-to-have items with explicit follow-ups.
- A short checklist entry in PR descriptions for anything merged that affects the demo (how to verify the fix).

Roles
- Moderator: runs the session, enforces time, keeps the triage focused.
- Timekeeper: visible countdown and warnings.
- Tech lead: runs the demo environment and confirms fixes (has remote/dev access).
- Note-taker: captures gaps and assigns them to owners in the board.

Artifacts to prepare (before and during day)
- One-slide per person with 3 bullets (for display only, not to be read word-for-word).
- A verification script for each critical flow (curl scripts, seeder, screenshot script).
- A shared issue board for triage.

Post-Day-18 follow-ups
- Merge any small fixes into the main branch with clear PR descriptions and verification steps.
- If anything remains, schedule a 30–60 minute final polish window before the public demo and assign owners.

Want this committed?
- I created `DEMO_REHEARSAL_DAY18.md` in the project root. I will now stage and commit it with the message: `Day 18 — Final confidence check: 90s solos and gap-resolution plan` and push it to the current branch so your team can pull it.