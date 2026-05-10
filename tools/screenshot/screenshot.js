const puppeteer = require('puppeteer');
const fs = require('fs');
const path = require('path');

(async () => {
  const outDir = path.resolve(__dirname, 'out');
  if (!fs.existsSync(outDir)) fs.mkdirSync(outDir, { recursive: true });

  // Base URL of the demo frontend
  const frontendFile = path.resolve(__dirname, '../../frontend/index.html');
  const frontendUrl = 'file://' + frontendFile;

  const browser = await puppeteer.launch({ headless: true, args: ['--no-sandbox', '--disable-setuid-sandbox'] });
  const page = await browser.newPage();
  await page.setViewport({ width: 1920, height: 1080 });

  // 1) Home / main screen
  await page.goto(frontendUrl, { waitUntil: 'networkidle0' });
  await page.waitForSelector('h1');
  await page.screenshot({ path: path.join(outDir, '01-home.png'), fullPage: true });
  console.log('Saved 01-home.png');

  // 2) Fill and submit Register form (note: backend must be running at the default URL to get a positive response)
  await page.type('#regFullName', 'Demo User');
  await page.type('#regEmail', 'demo+puppeteer@example.com');
  await page.type('#regPassword', 'password123');
  await Promise.all([
    page.click('#registerForm button[type=submit]'),
    page.waitForTimeout(1000),
  ]);
  // capture output box with the registration result
  await page.screenshot({ path: path.join(outDir, '02-register.png'), fullPage: true });
  console.log('Saved 02-register.png');

  // 3) After register, show output and then perform login
  await page.click('#loginEmail');
  await page.type('#loginEmail', 'demo+puppeteer@example.com');
  await page.type('#loginPassword', 'password123');
  await Promise.all([
    page.click('#loginForm button[type=submit]'),
    page.waitForTimeout(1000),
  ]);
  await page.screenshot({ path: path.join(outDir, '03-login.png'), fullPage: true });
  console.log('Saved 03-login.png');

  // 4) Click List Tools (requires backend to be running and the token stored from login)
  await Promise.all([
    page.click('#btnListTools'),
    page.waitForTimeout(1000),
  ]);
  await page.screenshot({ path: path.join(outDir, '04-tools-list.png'), fullPage: true });
  console.log('Saved 04-tools-list.png');

  // 5) Clear output to show a clean Tools card for the gallery
  await page.click('#btnClear');
  await page.screenshot({ path: path.join(outDir, '05-clean-tools.png'), fullPage: true });
  console.log('Saved 05-clean-tools.png');

  await browser.close();
  console.log('Screenshots complete: ' + outDir);
})();
