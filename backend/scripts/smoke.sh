#!/usr/bin/env bash
# Simple smoke script for the running backend (assumes backend on localhost:8080)
set -euo pipefail
BASE=http://localhost:8080
EMAIL="smoke$(date +%s)@example.com"
PASSWORD=smokepass
FULLNAME="Smoke User"

echo "Registering $EMAIL"
REGISTER_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$BASE/api/auth/register" -H 'Content-Type: application/json' -d "{\"fullName\":\"$FULLNAME\",\"email\":\"$EMAIL\",\"password\":\"$PASSWORD\"}")
HTTP=$(echo "$REGISTER_RESPONSE" | tail -n1)
BODY=$(echo "$REGISTER_RESPONSE" | sed '$d')
if [ "$HTTP" != "201" ]; then
  echo "Register failed (HTTP $HTTP)"; echo "$BODY"; exit 1
fi

echo "Login"
LOGIN=$(curl -s -X POST "$BASE/api/auth/login" -H 'Content-Type: application/json' -d "{\"email\":\"$EMAIL\",\"password\":\"$PASSWORD\"}")
TOKEN=$(echo "$LOGIN" | sed -n 's/.*"token":"\([^"]*\)".*/\1/p')
if [ -z "$TOKEN" ]; then
  echo "Login failed: $LOGIN"; exit 1
fi

echo "Listing tools"
curl -s -H "Authorization: Bearer $TOKEN" "$BASE/api/tools" | jq || true

echo "Smoke OK"
