#!/bin/bash

# Payments API Setup Script
# This script sets up the development environment for the Payments API

set -e

echo "🚀 Setting up Payments API development environment..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    print_error "Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    print_error "Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    print_warning "Maven is not installed. You can still use Docker, but local development will be limited."
fi

# Create necessary directories
print_status "Creating necessary directories..."
mkdir -p logs
mkdir -p init-scripts

# Create environment file if it doesn't exist
if [ ! -f .env ]; then
    print_status "Creating .env file..."
    cat > .env << EOF
# Database Configuration
POSTGRES_DB=payments_db
POSTGRES_USER=payments_user
POSTGRES_PASSWORD=payments_pass

# JWT Configuration
JWT_SECRET=mySecretKey123456789012345678901234567890
JWT_EXPIRATION=86400000

# Application Configuration
APP_DAILY_TRANSFER_LIMIT=10000.00
APP_AUDIT_ENABLED=true

# Rate Limiting Configuration
RATE_LIMIT_REQUESTS_PER_MINUTE=60
RATE_LIMIT_BURST_CAPACITY=10

# Spring Profile
SPRING_PROFILES_ACTIVE=docker
EOF
    print_status ".env file created with default values"
else
    print_status ".env file already exists"
fi

# Create database initialization script
print_status "Creating database initialization script..."
cat > init-scripts/01-init.sql << EOF
-- Create additional schemas or configurations if needed
-- This script runs when the PostgreSQL container starts for the first time

-- Enable extensions if needed
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create additional users or permissions if needed
-- GRANT ALL PRIVILEGES ON DATABASE payments_db TO payments_user;
EOF

# Build the application if Maven is available
if command -v mvn &> /dev/null; then
    print_status "Building the application with Maven..."
    mvn clean compile -DskipTests
    print_status "Application built successfully"
fi

# Start the services
print_status "Starting services with Docker Compose..."
docker-compose up -d postgres redis

# Wait for PostgreSQL to be ready
print_status "Waiting for PostgreSQL to be ready..."
timeout=60
counter=0
while ! docker-compose exec -T postgres pg_isready -U payments_user -d payments_db > /dev/null 2>&1; do
    if [ $counter -ge $timeout ]; then
        print_error "PostgreSQL failed to start within $timeout seconds"
        exit 1
    fi
    sleep 1
    counter=$((counter + 1))
done

print_status "PostgreSQL is ready!"

# Start the application
print_status "Starting the Payments API..."
docker-compose up -d payments-api

# Wait for the application to be ready
print_status "Waiting for the application to be ready..."
timeout=120
counter=0
while ! curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; do
    if [ $counter -ge $timeout ]; then
        print_error "Application failed to start within $timeout seconds"
        print_status "Check logs with: docker-compose logs payments-api"
        exit 1
    fi
    sleep 2
    counter=$((counter + 2))
done

print_status "Application is ready!"

# Display useful information
echo ""
echo "🎉 Setup completed successfully!"
echo ""
echo "📋 Service URLs:"
echo "   • API: http://localhost:8080/api"
echo "   • Swagger UI: http://localhost:8080/swagger-ui.html"
echo "   • Health Check: http://localhost:8080/actuator/health"
echo "   • PostgreSQL: localhost:5432"
echo "   • Redis: localhost:6379"
echo ""
echo "🔧 Useful commands:"
echo "   • View logs: docker-compose logs -f payments-api"
echo "   • Stop services: docker-compose down"
echo "   • Restart API: docker-compose restart payments-api"
echo "   • Run tests: mvn test"
echo "   • Build: mvn clean package"
echo ""
echo "📖 Documentation:"
echo "   • API Documentation: docs/API_Documentation.md"
echo "   • Security Checklist: docs/Security_Checklist.md"
echo "   • Architecture Diagram: docs/architecture-diagram.png"
echo ""
print_status "Happy coding! 🚀"

