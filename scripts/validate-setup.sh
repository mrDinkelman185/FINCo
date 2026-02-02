#!/bin/bash

# FINCo Setup Validation Script
# This script validates that the repository is correctly set up

set -e

echo "==================================="
echo "FINCo Setup Validation"
echo "==================================="
echo ""

ERRORS=0

# Check required files exist
echo "Checking required files..."
REQUIRED_FILES=(
    "README.md"
    "CONTRIBUTING.md"
    "ARCHITECTURE.md"
    "DEPLOYMENT.md"
    "SECURITY.md"
    "LICENSE"
    "Makefile"
    "docker-compose.yml"
    ".env.example"
    ".gitignore"
    ".snyk"
    "backend/pom.xml"
    "backend/Dockerfile"
    "frontend/package.json"
    "frontend/Dockerfile"
    "terraform/main.tf"
)

for file in "${REQUIRED_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "✓ $file"
    else
        echo "✗ $file - MISSING"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""
echo "Checking required directories..."
REQUIRED_DIRS=(
    "backend/src/main/java"
    "backend/src/main/resources"
    "backend/src/test/java"
    "frontend/src/app"
    "frontend/src/lib"
    "frontend/src/types"
    "terraform/modules/vpc"
    "terraform/modules/ecs"
    "terraform/modules/rds"
    "terraform/modules/elasticache"
    "terraform/modules/alb"
    ".github/workflows"
)

for dir in "${REQUIRED_DIRS[@]}"; do
    if [ -d "$dir" ]; then
        echo "✓ $dir"
    else
        echo "✗ $dir - MISSING"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""
echo "Checking GitHub Actions workflows..."
WORKFLOWS=(
    ".github/workflows/backend-build.yml"
    ".github/workflows/frontend-build.yml"
    ".github/workflows/docker-build.yml"
    ".github/workflows/security-scan.yml"
    ".github/workflows/deploy.yml"
    ".github/workflows/terraform.yml"
)

for workflow in "${WORKFLOWS[@]}"; do
    if [ -f "$workflow" ]; then
        echo "✓ $workflow"
    else
        echo "✗ $workflow - MISSING"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""
echo "==================================="
if [ $ERRORS -eq 0 ]; then
    echo "✓ All checks passed!"
    echo "==================================="
    exit 0
else
    echo "✗ $ERRORS error(s) found"
    echo "==================================="
    exit 1
fi
