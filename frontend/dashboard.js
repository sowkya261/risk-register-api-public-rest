const baseUrl = (new URL('.', window.location.href)).origin.replace(/\/dashboard.html$/, '') + '/api';

async function fetchKpis() {
  try {
    const r = await fetch(baseUrl + '/dashboard/kpis');
    const j = await r.json();
    const data = j.data || {};
    const kpiRoot = document.getElementById('kpis');
    kpiRoot.innerHTML = '';
    ['totalTools','toolsLast7Days','activeUsers','pendingReminders'].forEach(k => {
      const el = document.createElement('div'); el.className='kpi'; el.innerHTML = `<strong>${k}</strong><div>${data[k] ?? '-'}</div>`;
      kpiRoot.appendChild(el);
    });
  } catch (e) { console.error(e); }
}

async function loadTools(search) {
  const tb = document.querySelector('#toolsTable tbody');
  tb.innerHTML = '';
  try {
    const r = await fetch(baseUrl + '/tools?search=' + encodeURIComponent(search||''));
    const j = await r.json();
    const page = j.data;
    (page.content||[]).forEach(t => {
      const tr = document.createElement('tr');
      tr.innerHTML = `<td>${t.id}</td><td>${t.name}</td><td>${t.owner ?? '-'}</td><td>${t.createdAt ?? '-'}</td><td>${t.active}</td><td><button data-id="${t.id}" class="btnDel">Delete</button></td>`;
      tb.appendChild(tr);
    });
  } catch(e){ console.error(e); }
}

async function exportCsv() {
  const r = await fetch(baseUrl + '/dashboard/export/tools.csv');
  const text = await r.text();
  const blob = new Blob([text], { type: 'text/csv' });
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a'); a.href = url; a.download = 'tools.csv'; document.body.appendChild(a); a.click(); a.remove();
}

async function loadAudit() {
  try {
    const r = await fetch(baseUrl + '/audit?limit=10');
    const j = await r.json();
    const ul = document.getElementById('auditList'); ul.innerHTML = '';
    (j.data||[]).forEach(a => { const li = document.createElement('li'); li.textContent = `${a.createdAt}: ${a.username} ${a.action} ${a.targetId}`; ul.appendChild(li); });
  } catch(e){ console.error(e); }
}

document.getElementById('btnRefresh').addEventListener('click', () => { fetchKpis(); loadTools(document.getElementById('searchBox').value); loadAudit(); });
document.getElementById('btnExport').addEventListener('click', exportCsv);
document.getElementById('searchBox').addEventListener('input', (e) => { setTimeout(()=> loadTools(e.target.value), 200); });

// initial load
fetchKpis(); loadTools(''); loadAudit();

// delegate delete
document.querySelector('#toolsTable tbody').addEventListener('click', async (ev) => {
  if (ev.target.matches('.btnDel')) {
    const id = ev.target.getAttribute('data-id');
    if (!confirm('Delete tool ' + id + '?')) return;
    await fetch(baseUrl + '/tools/' + id, { method: 'DELETE' });
    loadTools(document.getElementById('searchBox').value);
    loadAudit();
  }
});
