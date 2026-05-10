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
  docker-compose up -d --build
  echo "Stack started. Backend should be available at http://localhost:8080"
  exit 0
fi

if [[ "$CMD" == "run" ]]; then
  echo "Running backend from source (maven)..."
  cd "$BACKEND_DIR"
  mvn -Dspring-boot.run.profiles=dev spring-boot:run
  exit 0
fi

echo "Unknown command: $CMD"
exit 2
