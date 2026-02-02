-- Initial database setup script
-- This runs automatically when the postgres container starts

-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS public;

-- Grant privileges
GRANT ALL PRIVILEGES ON SCHEMA public TO finco_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO finco_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO finco_user;

-- Create sample account for testing
INSERT INTO accounts (account_number, account_name, account_type, balance, currency, status, created_at, updated_at)
VALUES ('ACC-001', 'Demo Trading Account', 'TRADING', 100000.00, 'USD', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (account_number) DO NOTHING;
