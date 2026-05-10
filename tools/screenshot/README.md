# Screenshot generator

This small tool uses Puppeteer to create 5 screenshots of the demo frontend at 1920x1080:

1. Home / main screen
2. Register attempt (fills form and submits)
3. Login attempt (fills form and submits)
4. Tools list (clicks "List Tools")
5. Clean tools card (clears output for a clean shot)

Prerequisites
- Node.js 18+ installed
- Puppeteer is installed (this package uses the local Chromium downloaded by Puppeteer)

Run

```bash
cd tools/screenshot
npm install
npm run screenshot
```

Notes
- The script opens the local frontend `frontend/index.html` directly (file://) and will submit to the backend using the base URL set in the frontend input (default `http://localhost:8080/api`). For best results run the backend locally before capturing screenshots so the register/login/list flows succeed.
- Output images are written to `tools/screenshot/out`.
