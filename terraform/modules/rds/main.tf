resource "random_password" "db_password" {
  length  = 32
  special = true
}

resource "aws_secretsmanager_secret" "db_password" {
  name = "${var.environment}-finco-db-password"
}

resource "aws_secretsmanager_secret_version" "db_password" {
  secret_id     = aws_secretsmanager_secret.db_password.id
  secret_string = random_password.db_password.result
}

resource "aws_db_subnet_group" "main" {
  name       = "${var.environment}-finco-db-subnet"
  subnet_ids = var.private_subnet_ids

  tags = {
    Name = "${var.environment}-finco-db-subnet"
  }
}

resource "aws_security_group" "rds" {
  name        = "${var.environment}-finco-rds-sg"
  description = "Security group for RDS"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.environment}-finco-rds-sg"
  }
}

resource "aws_db_instance" "main" {
  identifier             = "${var.environment}-finco-db"
  engine                 = "postgres"
  engine_version         = "16.1"
  instance_class         = var.instance_class
  allocated_storage      = 20
  storage_type           = "gp3"
  storage_encrypted      = true
  db_name                = var.database_name
  username               = var.database_username
  password               = random_password.db_password.result
  db_subnet_group_name   = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.rds.id]
  multi_az               = var.multi_az
  skip_final_snapshot    = true
  backup_retention_period = 7
  enabled_cloudwatch_logs_exports = ["postgresql", "upgrade"]

  tags = {
    Name = "${var.environment}-finco-db"
  }
}
