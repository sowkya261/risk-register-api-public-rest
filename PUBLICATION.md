Publishing the repository (public) — Day 15

Goal
----
Make this repository public and share a single link with your mentor. Ensure no secrets are exposed and the repo is ready for a code review or demo.

Checklist before publishing
- [x] Remove tracked secrets (no tracked `.env` with real passwords). The repo already ignores `.env` and `.env` is not tracked.
- [x] Provide `backend/.env.example` (safe defaults and placeholders) — already present and cleaned.
- [x] Ensure `.gitignore` excludes local secrets and build artifacts. Check `backend/.gitignore` includes `.env` and target/ directories.
- [x] Confirm README.md describes how to run locally and which env vars are required.
- [x] Add demo rehearsal and start script for reviewers (DEMO_REHEARSAL_DAY14.md, scripts/start_demo.sh).

Recommended one-commit-per-day policy
- You indicated "one commit per day" — keep a clean history on the branch you will publish (e.g., `java-dev1`).
- Do not rewrite published history after sharing the link; create follow-up commits for fixes on subsequent days.

How to make the repo public on GitHub
1. Go to the repository settings on GitHub: Settings -> General -> Change repository visibility -> Make public.
2. Confirm the warning and proceed.
3. After making the repo public, copy the URL from the browser (e.g., https://github.com/<owner>/<repo>) and share it with your mentor.

What to share with your mentor
- Repo URL (public link), branch (defaults to `java-dev1`), and a short note with which commit/day snapshot they should review.
- Example message:
  "Hi — here's the project for review: https://github.com/sowkya261/risk-register-api-public-rest (branch: java-dev1). Please review Day 1–15 work; start with README and DEMO_REHEARSAL_DAY14.md."

If you discover a secret after publishing
- Do NOT rewrite history on the public repo without coordination. Instead:
  1. Revoke the secret (rotate passwords, regenerate tokens).
  2. Remove the secret from the repo (delete file / update .gitignore) and commit a fix.
  3. If you must scrub history, coordinate with the mentor/maintainers — use bfg-repo-cleaner or git filter-repo and then force-push, but be aware this affects all forks and collaborators.

Need me to do this for you?
- I can: create a minimal GitHub Actions workflow to run unit tests on every push; add `backend/.env.example` to ensure completeness; produce a short message to send to your mentor.
- Tell me which of those you'd like and I will implement it as Day 16 work.
