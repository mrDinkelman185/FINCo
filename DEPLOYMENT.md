# FINCo Deployment Guide

## Prerequisites

- Docker and Docker Compose
- Java 21 JDK (for local development)
- Node.js 20+ (for local development)
- AWS CLI configured (for AWS deployment)
- Terraform 1.5+ (for infrastructure provisioning)

## Local Development

### Using Docker Compose (Recommended)

This is the easiest way to run the entire application stack locally:

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Remove volumes (clean slate)
docker-compose down -v
```

Access the application:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api/v1
- Health Check: http://localhost:8080/actuator/health

### Running Services Individually

#### Backend

```bash
cd backend

# Build
./mvnw clean package

# Run
./mvnw spring-boot:run

# Or run the jar
java -jar target/*.jar
```

#### Frontend

```bash
cd frontend

# Install dependencies
npm install

# Run in development mode
npm run dev

# Build for production
npm run build
npm start
```

## AWS Deployment

### 1. Prerequisites

Set up AWS credentials:

```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=us-east-1
```

### 2. Build and Push Docker Images

```bash
# Build images
docker-compose build

# Tag for ECR
docker tag finco-backend:latest 123456789012.dkr.ecr.us-east-1.amazonaws.com/finco-backend:latest
docker tag finco-frontend:latest 123456789012.dkr.ecr.us-east-1.amazonaws.com/finco-frontend:latest

# Login to ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 123456789012.dkr.ecr.us-east-1.amazonaws.com

# Push images
docker push 123456789012.dkr.ecr.us-east-1.amazonaws.com/finco-backend:latest
docker push 123456789012.dkr.ecr.us-east-1.amazonaws.com/finco-frontend:latest
```

### 3. Provision Infrastructure with Terraform

```bash
cd terraform

# Initialize Terraform
terraform init

# Create a terraform.tfvars file (copy from terraform.tfvars.example)
cp terraform.tfvars.example terraform.tfvars
# Edit terraform.tfvars with your values

# Plan infrastructure changes
terraform plan

# Apply changes
terraform apply
```

### 4. Deploy Application

The GitHub Actions workflow will automatically deploy on push to main branch, or you can manually deploy:

```bash
# Update ECS service
aws ecs update-service \
  --cluster finco-cluster \
  --service finco-backend \
  --force-new-deployment

aws ecs update-service \
  --cluster finco-cluster \
  --service finco-frontend \
  --force-new-deployment
```

## Multi-Region Deployment

To enable multi-region deployment:

1. Update `terraform.tfvars`:
```hcl
aws_region = "us-west-2"  # Secondary region
```

2. Apply Terraform in the secondary region:
```bash
cd terraform
terraform workspace new us-west-2
terraform apply
```

3. Set up Route53 for traffic management between regions.

## Environment Variables

### Backend

- `SPRING_DATASOURCE_URL` - PostgreSQL connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_REDIS_HOST` - Redis host
- `SPRING_REDIS_PASSWORD` - Redis password
- `JWT_SECRET` - JWT signing secret
- `AWS_REGION` - AWS region for multi-region support

### Frontend

- `NEXT_PUBLIC_API_URL` - Backend API URL

## Monitoring

### Health Checks

- Backend: `GET /actuator/health`
- Frontend: `GET /` (returns 200 if healthy)

### Metrics

Backend exposes Prometheus metrics at `/actuator/prometheus`.

### Logs

- Local: `docker-compose logs -f`
- AWS: CloudWatch Logs under `/ecs/finco-*`

## Troubleshooting

### Database Connection Issues

```bash
# Check database is running
docker-compose ps postgres

# Access database
docker-compose exec postgres psql -U finco_user -d finco_db

# Check backend logs
docker-compose logs backend
```

### Redis Connection Issues

```bash
# Check Redis is running
docker-compose ps redis

# Test Redis connection
docker-compose exec redis redis-cli ping
```

### ECS Deployment Issues

```bash
# Check ECS service status
aws ecs describe-services --cluster finco-cluster --services finco-backend

# Check task logs
aws logs tail /ecs/finco-backend --follow
```

## Security Considerations

1. Never commit secrets to the repository
2. Use AWS Secrets Manager for production secrets
3. Enable encryption at rest for RDS and ElastiCache
4. Use VPC endpoints for AWS services
5. Enable CloudWatch logging for all services
6. Regularly update dependencies (check Snyk reports)
7. Review CodeQL security findings

## Rollback

To rollback a deployment:

```bash
# Get previous task definition
aws ecs describe-task-definition --task-definition finco-backend

# Update service with previous revision
aws ecs update-service \
  --cluster finco-cluster \
  --service finco-backend \
  --task-definition finco-backend:PREVIOUS_REVISION
```

## Scaling

### Manual Scaling

```bash
# Scale ECS service
aws ecs update-service \
  --cluster finco-cluster \
  --service finco-backend \
  --desired-count 4
```

### Auto Scaling

Configure auto-scaling in Terraform or via AWS Console:
- Target tracking scaling based on CPU/Memory
- Target tracking based on ALB request count
- Scheduled scaling for predictable traffic patterns
