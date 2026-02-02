# FINCo - Financial Trading Platform

[![Backend Build](https://github.com/mrDinkelman185/FINCo/actions/workflows/backend-build.yml/badge.svg)](https://github.com/mrDinkelman185/FINCo/actions/workflows/backend-build.yml)
[![Frontend Build](https://github.com/mrDinkelman185/FINCo/actions/workflows/frontend-build.yml/badge.svg)](https://github.com/mrDinkelman185/FINCo/actions/workflows/frontend-build.yml)
[![Security Scan](https://github.com/mrDinkelman185/FINCo/actions/workflows/security-scan.yml/badge.svg)](https://github.com/mrDinkelman185/FINCo/actions/workflows/security-scan.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A comprehensive, production-ready fintech trading platform built with modern technologies and cloud-native architecture. Features order management, position tracking, compliance frameworks, and multi-region deployment capabilities.

## Architecture

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Cache**: Redis
- **Features**: 
  - Order Management System
  - Position Tracking
  - FIX Protocol Support (placeholder)
  - Compliance Framework (placeholder)
  - Multi-region Ready

### Frontend
- **Language**: TypeScript
- **Framework**: Next.js 15+ with React 19
- **UI Components**: Modern trading dashboard
- **Features**:
  - Real-time order management
  - Position tracking interface
  - Trading analytics

### Infrastructure
- **Containerization**: Docker & Docker Compose
- **Orchestration**: AWS ECS/EKS
- **IaC**: Terraform
- **CI/CD**: GitHub Actions
- **Security**: Snyk, Trivy, CodeQL

## Prerequisites

- Java 21
- Node.js 20+ and npm/yarn
- Docker & Docker Compose
- AWS CLI (for deployment)
- Terraform (for infrastructure)

## Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/mrDinkelman185/FINCo.git
cd FINCo
```

### 2. Environment Setup
```bash
# Copy environment template
cp .env.example .env

# Update with your configuration
```

### 3. Run with Docker Compose
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### 4. Local Development

#### Backend
```bash
cd backend
./mvnw spring-boot:run
```

#### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        AWS Cloud (Multi-Region)              │
├─────────────────────────────────────────────────────────────┤
│  ┌──────────────┐     ┌──────────────┐    ┌──────────────┐ │
│  │   ECS/EKS    │────▶│  RDS (PG)    │    │ElastiCache   │ │
│  │   Cluster    │     │  Multi-AZ    │    │   (Redis)    │ │
│  └──────────────┘     └──────────────┘    └──────────────┘ │
│         │                                                    │
│  ┌──────▼──────┐                                            │
│  │     ALB     │                                            │
│  └─────────────┘                                            │
└─────────────────────────────────────────────────────────────┘
         │
         ▼
┌─────────────────┐          ┌─────────────────┐
│   Frontend      │◀────────▶│    Backend      │
│   (Next.js)     │   API    │  (Spring Boot)  │
└─────────────────┘          └─────────────────┘
```

## API Endpoints

### Orders
- `POST /api/v1/orders` - Create new order
- `GET /api/v1/orders` - List all orders
- `GET /api/v1/orders/{id}` - Get order details
- `PUT /api/v1/orders/{id}` - Update order
- `DELETE /api/v1/orders/{id}` - Cancel order

### Positions
- `GET /api/v1/positions` - List all positions
- `GET /api/v1/positions/{symbol}` - Get position by symbol

### Health
- `GET /actuator/health` - Service health check

## Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Integration Tests
```bash
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

## CI/CD Pipeline

GitHub Actions workflows automatically:
1. Build and test on every push
2. Run security scans (Snyk, Trivy, CodeQL)
3. Lint code (Checkstyle, ESLint)
4. Deploy to AWS (on main branch)

## Security

- All secrets managed via environment variables
- Snyk policy for vulnerability management
- Container scanning with Trivy
- Code analysis with CodeQL
- No secrets committed to repository

## Infrastructure

Terraform configurations in `/terraform` directory:
- VPC with public/private subnets
- ECS/EKS cluster setup
- RDS PostgreSQL (Multi-AZ)
- ElastiCache Redis
- Application Load Balancer
- Multi-region support

## Compliance

Placeholder implementations for:
- FIX Protocol integration
- Trade reporting
- Audit logging
- Regulatory compliance checks

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## Documentation

- [Architecture Documentation](ARCHITECTURE.md) - Detailed system architecture
- [Deployment Guide](DEPLOYMENT.md) - Complete deployment instructions
- [Contributing Guide](CONTRIBUTING.md) - How to contribute

## Project Structure

```
FINCo/
├── backend/              # Java Spring Boot backend
├── frontend/             # Next.js/React frontend  
├── terraform/            # Infrastructure as Code
├── .github/workflows/    # CI/CD pipelines
├── docker-compose.yml    # Local development setup
└── Makefile             # Common operations
```

## Quick Commands

Using Make:
```bash
make up          # Start all services
make down        # Stop all services
make logs        # View logs
make test        # Run all tests
make help        # Show all commands
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For issues and questions, please open a GitHub issue.