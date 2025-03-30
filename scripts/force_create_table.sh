#!/bin/bash

# Connect to PostgreSQL and execute the SQL
psql -h localhost -p 5432 -U postgres -d urlshortener << EOF

-- Drop the table if it exists
DROP TABLE IF EXISTS urls;

-- Create the urls table
CREATE TABLE urls (
    short_key VARCHAR(10) PRIMARY KEY,
    original_url TEXT NOT NULL,
    created_at BIGINT NOT NULL
);

-- Create a unique index on short_key
CREATE UNIQUE INDEX urls_short_key_idx ON urls (short_key);

-- Display the table structure
\d urls

-- Insert a test record
INSERT INTO urls (short_key, original_url, created_at)
VALUES ('test123', 'https://www.example.com', extract(epoch from now()) * 1000);

-- Verify the data
SELECT * FROM urls;

EOF

echo "Table creation script completed."
