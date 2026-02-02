# Quick Start Guide

Get FINCo up and running in 5 minutes!

## Prerequisites

- Docker Desktop installed and running
- Git installed
- 8GB RAM available
- 10GB disk space

## Step 1: Clone the Repository

```bash
git clone https://github.com/mrDinkelman185/FINCo.git
cd FINCo
```

## Step 2: Set Up Environment

```bash
# Copy environment template
cp .env.example .env

# (Optional) Edit .env if you want to change defaults
# Default credentials are fine for local development
```

## Step 3: Start the Application

```bash
# Using Docker Compose
docker compose up -d

# Or using Make
make up
```

This will start:
- PostgreSQL database on port 5432
- Redis cache on port 6379
- Backend API on port 8080
- Frontend UI on port 3000

## Step 4: Access the Application

Open your browser and navigate to:

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api/v1/orders
- **Health Check**: http://localhost:8080/actuator/health

## Step 5: Try Creating an Order

1. Go to http://localhost:3000
2. Fill in the order form:
   - Account ID: 1 (demo account is pre-created)
   - Symbol: AAPL
   - Side: BUY
   - Order Type: MARKET
   - Quantity: 10
3. Click "Place Order"

## Common Commands

```bash
make up          # Start all services
make down        # Stop all services
make logs        # View logs
make test        # Run tests
make help        # Show all commands
```

## Need Help?

- Check the logs: `docker compose logs`
- Review the [Documentation](README.md)
- Open an issue on GitHub

Happy trading!
