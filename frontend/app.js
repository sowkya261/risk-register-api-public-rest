const baseUrlInput = document.getElementById('baseUrl');
const out = document.getElementById('output');
let token = null;

function write(o) {
  out.textContent += JSON.stringify(o, null, 2) + '\n';
}

document.getElementById('registerForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const fullName = document.getElementById('regFullName').value;
  const email = document.getElementById('regEmail').value;
  const password = document.getElementById('regPassword').value;
  const url = baseUrlInput.value + '/auth/register';
  try {
    const resp = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ fullName, email, password })
    });
    const json = await resp.json();
    write({ status: resp.status, body: json });
  } catch (err) {
    write({ error: String(err) });
  }
});

document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const email = document.getElementById('loginEmail').value;
  const password = document.getElementById('loginPassword').value;
  const url = baseUrlInput.value + '/auth/login';
  try {
    const resp = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    const json = await resp.json();
    write({ status: resp.status, body: json });
    if (json && json.data && json.data.token) {
      token = json.data.token;
      write({ info: 'Stored token for subsequent requests' });
    }
  } catch (err) {
    write({ error: String(err) });
  }
});

document.getElementById('btnListTools').addEventListener('click', async () => {
  const url = baseUrlInput.value + '/tools';
  try {
    const headers = token ? { 'Authorization': 'Bearer ' + token } : {};
    const resp = await fetch(url, { headers });
    const json = await resp.json();
    write({ status: resp.status, body: json });
  } catch (err) {
    write({ error: String(err) });
  }
});

document.getElementById('btnClear').addEventListener('click', () => { out.textContent = ''; });
