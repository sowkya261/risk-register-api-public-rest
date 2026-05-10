# Day 17 — Rehearsal 2: Full Team, 6-minute target

Goal
- Run a tight, full-team 6-minute rehearsal where every member answers the same 5 key questions without notes. The exercise trains concise storytelling, clarity under time pressure, and ensures each person can speak to the project confidently.

Quick plan (one-liner)
- Moderator opens (15s), each team member answers 5 questions in their allocated slot (time per member depends on team size), quick 30s team debrief.

Before you start (prep checklist)
- Decide team size N (how many people will speak). Recommended: 4–6 people for a 6-minute run; you can still use the template for any N.
- Assign roles: Moderator/Host (keeps time and prompts), Timekeeper (visual timer), Note-taker (captures issues), Tech lead (ensures slides or demo pages load). These can be separate or combined.
- Ensure a visible countdown timer is available (phone, browser timer, or presenter view). Use a 0:30 warning for each speaker.
- All participants should have the 5 questions printed or on a single slide — but they must not hold notes while answering.
- Confirm camera/mic/test audio if recording.

Format & timing
- Total time: 6:00 (360 seconds)
- Moderator intro: 15s
- Speaker slots: remainder minus 30s debrief
- Team debrief: 30s

Compute time per speaker
- Time available for speakers = 360 - 15 - 30 = 315 seconds
- Per-speaker time = floor(315 / N) seconds
- Reserve the final surplus seconds (315 - per_speaker_time * N) for transitions (moderator cues).

Examples (rounded):
- N=4 → per-speaker ≈ 78s (1:18)
- N=5 → per-speaker ≈ 63s (1:03)
- N=6 → per-speaker ≈ 52s (0:52)
- N=8 → per-speaker ≈ 39s (0:39)

Moderator script (concise)
- "We have 6 minutes. I'll give each speaker X seconds to answer five quick questions without notes. I will cue you at 30s left and stop you at the end of your slot. Ready? Go." (15s)
- For each speaker: "Name — your time starts now." Cue at 30s left: "30 seconds remaining." At time up: "Time!" Move to next.

The five key questions (ask these, in this order)
1. "In one sentence, what's the project and who does it help?" (Goal: crisp elevator pitch)
2. "What's the single most important technical decision you made or contributed to?" (Goal: highlights ownership/impact)
3. "What's one risk or hard problem you solved (or would call out) and how you mitigated it?" (Goal: shows troubleshooting)
4. "What's a measurable result or piece of validation that shows the project works?" (Goal: demos/tests/metrics)
5. "If you had one minute of follow-up, what would you ask the reviewer to look at or try first?" (Goal: next-step ask)

Guidance for answers (be concise)
- Aim for: 15–30s for the first (elevator) question, 10–20s for questions 2–4, 10–15s for the last depending on slot length.
- Use the structure: (1) One-liner, (2) action/decision, (3) result/why it mattered.
- No slides or notes during the answers — this enforces memorized core points.

Scoring rubric (fast feedback, optional)
- Per question: 0 (not answered / unclear), 1 (adequate), 2 (clear + impact). Max per speaker: 10.
- After the run, note total per-speaker and highlight 1 improvement (tone, clarity, missing evidence).

Coach/timekeeper cues
- 3-second handshake before the speaker: moderator says speaker name and then counts down 3..2..1..start.
- 30s remaining beep or vocal cue.
- End-of-slot gentle stop and next name.

Common failure modes and mitigations
- Speaker overruns: enforce strict stop; have the moderator step in (practice discipline).
- Someone freezes: Moderator can allow a single 5–10s prompt ("focus on question 1: elevator pitch"). If still stuck, move on and capture for one-to-one coaching.
- Technical demo fails: keep the rehearsal verbal-only; demo verification can be a separate technical dry run.

Optional variants
- Lightning round: every team member gets 30s to answer only questions 1–2 (useful for large teams).
- Peer scoring: after each speaker, one peer gives a 10s verbal tip (only if time allows).
- Recorded run: record the session and timestamp each speaker for post-mortem.

Post-run 30s debrief (exact)
- Moderator: "Two quick wins and one improvement." (10s)
- Note-taker: call out immediate blockers or items to practice next (20s)

Materials to prepare and share
- Single-slide with the five questions and per-speaker time (visible during run)
- Timer URL (e.g., https://timer.onlineclock.net/ or a phone) and recording consent
- Scorecard template (one row per speaker with 5 columns for the five questions)

Follow-up practice suggestions
- Repeat the drill twice: first blind (no notes), second with 10% faster pacing. Aim to compress without losing clarity.
- Pair off for 5-minute micro-coaching (one coach, one speaker) focusing on tightening answers.

Want this added to the repo as a committed file?
- I created `DEMO_REHEARSAL_DAY17.md` in the project root so the whole team can pull it. If you'd like modifications (different timing, alternative questions, or a printable scorecard CSV), tell me which and I'll update the file.

Good luck — when you're ready I can also generate a one-page printable timer slide or a CSV scorecard for easy live scoring.