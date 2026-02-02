variable "aws_region" {
  description = "AWS region for resources"
  type        = string
  default     = "us-east-1"
}

variable "environment" {
  description = "Environment name (dev, staging, prod)"
  type        = string
  default     = "dev"
}

variable "vpc_cidr" {
  description = "CIDR block for VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "availability_zones" {
  description = "List of availability zones"
  type        = list(string)
  default     = ["us-east-1a", "us-east-1b", "us-east-1c"]
}

variable "database_name" {
  description = "Name of the database"
  type        = string
  default     = "finco_db"
}

variable "database_username" {
  description = "Database master username"
  type        = string
  default     = "finco_admin"
}

variable "rds_multi_az" {
  description = "Enable Multi-AZ for RDS"
  type        = bool
  default     = true
}

variable "rds_instance_class" {
  description = "RDS instance class"
  type        = string
  default     = "db.t3.medium"
}

variable "redis_node_type" {
  description = "ElastiCache node type"
  type        = string
  default     = "cache.t3.micro"
}

variable "redis_num_nodes" {
  description = "Number of ElastiCache nodes"
  type        = number
  default     = 2
}

variable "backend_image" {
  description = "Docker image for backend"
  type        = string
  default     = "finco-backend:latest"
}

variable "frontend_image" {
  description = "Docker image for frontend"
  type        = string
  default     = "finco-frontend:latest"
}

variable "backend_cpu" {
  description = "CPU units for backend (1024 = 1 vCPU)"
  type        = number
  default     = 512
}

variable "backend_memory" {
  description = "Memory for backend in MB"
  type        = number
  default     = 1024
}

variable "frontend_cpu" {
  description = "CPU units for frontend"
  type        = number
  default     = 256
}

variable "frontend_memory" {
  description = "Memory for frontend in MB"
  type        = number
  default     = 512
}
