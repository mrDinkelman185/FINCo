.PHONY: help up down logs build test clean backend-test frontend-test

help: ## Show this help message
@echo 'Usage: make [target]'
@echo ''
@echo 'Available targets:'
@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  %-20s %s\n", $$1, $$2}'

up: ## Start all services
docker compose up -d

down: ## Stop all services
docker compose down

down-v: ## Stop all services and remove volumes
docker compose down -v

logs: ## Show logs from all services
docker compose logs -f

logs-backend: ## Show backend logs
docker compose logs -f backend

logs-frontend: ## Show frontend logs
docker compose logs -f frontend

build: ## Build all Docker images
docker compose build

build-backend: ## Build backend Docker image
docker compose build backend

build-frontend: ## Build frontend Docker image
docker compose build frontend

test: backend-test frontend-test ## Run all tests

backend-test: ## Run backend tests
cd backend && mvn test

frontend-test: ## Run frontend tests
cd frontend && npm test

backend-lint: ## Run backend linting
cd backend && mvn checkstyle:check

frontend-lint: ## Run frontend linting
cd frontend && npm run lint

backend-build: ## Build backend
cd backend && mvn clean package

frontend-build: ## Build frontend
cd frontend && npm run build

dev-backend: ## Run backend in development mode
cd backend && mvn spring-boot:run

dev-frontend: ## Run frontend in development mode
cd frontend && npm run dev

clean: ## Clean build artifacts
cd backend && mvn clean
cd frontend && rm -rf .next node_modules

db-shell: ## Connect to PostgreSQL database
docker compose exec postgres psql -U finco_user -d finco_db

redis-shell: ## Connect to Redis
docker compose exec redis redis-cli

status: ## Show status of all services
docker compose ps

restart: down up ## Restart all services
