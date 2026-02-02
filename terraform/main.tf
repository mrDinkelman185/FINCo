terraform {
  required_version = ">= 1.5.0"
  
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {
    # Configure backend with:
    # bucket         = "finco-terraform-state"
    # key            = "finco/terraform.tfstate"
    # region         = "us-east-1"
    # encrypt        = true
    # dynamodb_table = "finco-terraform-lock"
  }
}

provider "aws" {
  region = var.aws_region

  default_tags {
    tags = {
      Project     = "FINCo"
      ManagedBy   = "Terraform"
      Environment = var.environment
    }
  }
}

# VPC Module
module "vpc" {
  source = "./modules/vpc"

  environment         = var.environment
  vpc_cidr            = var.vpc_cidr
  availability_zones  = var.availability_zones
  enable_nat_gateway  = true
  enable_vpn_gateway  = false
}

# ALB Module
module "alb" {
  source = "./modules/alb"

  environment        = var.environment
  vpc_id             = module.vpc.vpc_id
  public_subnet_ids  = module.vpc.public_subnet_ids
}

# RDS Module
module "rds" {
  source = "./modules/rds"

  environment         = var.environment
  vpc_id              = module.vpc.vpc_id
  private_subnet_ids  = module.vpc.private_subnet_ids
  database_name       = var.database_name
  database_username   = var.database_username
  multi_az            = var.rds_multi_az
  instance_class      = var.rds_instance_class
}

# ElastiCache Module
module "elasticache" {
  source = "./modules/elasticache"

  environment         = var.environment
  vpc_id              = module.vpc.vpc_id
  private_subnet_ids  = module.vpc.private_subnet_ids
  node_type           = var.redis_node_type
  num_cache_nodes     = var.redis_num_nodes
}

# ECS Module
module "ecs" {
  source = "./modules/ecs"

  environment            = var.environment
  vpc_id                 = module.vpc.vpc_id
  private_subnet_ids     = module.vpc.private_subnet_ids
  alb_target_group_arn   = module.alb.backend_target_group_arn
  alb_security_group_id  = module.alb.alb_security_group_id
  
  # Database connection
  db_host                = module.rds.db_endpoint
  db_name                = var.database_name
  db_username            = var.database_username
  db_password_secret_arn = module.rds.db_password_secret_arn
  
  # Redis connection
  redis_host             = module.elasticache.redis_endpoint
  
  # Application settings
  backend_image          = var.backend_image
  frontend_image         = var.frontend_image
  backend_cpu            = var.backend_cpu
  backend_memory         = var.backend_memory
  frontend_cpu           = var.frontend_cpu
  frontend_memory        = var.frontend_memory
}
