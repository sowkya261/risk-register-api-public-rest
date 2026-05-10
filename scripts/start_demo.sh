#!/usr/bin/env bash
# Helper script to start the demo stack quickly.
# Usage: ./scripts/start_demo.sh docker|run

set -euo pipefail
CMD=${1:-docker}
ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
BACKEND_DIR="$ROOT_DIR/backend"

if [[ "$CMD" == "docker" ]]; then
  echo "Starting Docker Compose stack (backend, postgres, redis)..."
  if ! command -v docker >/dev/null 2>&1; then
    echo "Docker is not installed or not on PATH. Aborting."
    exit 1
  fi
  cd "$BACKEND_DIR"
  # prefer docker-compose binary if available, otherwise use 'docker compose' plugin
  if command -v docker-compose >/dev/null 2>&1; then
    docker-compose up -d --build
  else
    echo "docker-compose not found; trying 'docker compose' plugin"
    docker compose up -d --build
  fi
  echo "Stack started. Backend should be available at http://localhost:8080"
  exit 0
fi

if [[ "$CMD" == "run" ]]; then
  echo "Running backend from source (maven)..."
  cd "$BACKEND_DIR"
  mvn -Dspring-boot.run.profiles=dev spring-boot:run &
  BACKEND_PID=$!
  # serve frontend on port 8081
  echo "Serving frontend on http://localhost:8081"
  (cd "$ROOT_DIR/frontend" && npx http-server -c-1 . -p 8081) &
  FRONTEND_PID=$!
  echo "Backend PID: $BACKEND_PID frontend PID: $FRONTEND_PID"
  exit 0
fi

echo "Unknown command: $CMD"
exit 2
