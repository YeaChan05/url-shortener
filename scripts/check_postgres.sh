#!/bin/bash

# Check if PostgreSQL is installed
if ! command -v psql &> /dev/null; then
    echo "PostgreSQL is not installed. Please install it first."
    exit 1
fi

# Check if PostgreSQL server is running
if ! pg_isready -h localhost -p 5432 &> /dev/null; then
    echo "PostgreSQL server is not running. Please start it first."
    exit 1
fi

# Try to create the database (will not error if it already exists)
psql -h localhost -p 5432 -U postgres -c "CREATE DATABASE urlshortener;" 2>/dev/null

# Check if the database exists
if psql -h localhost -p 5432 -U postgres -lqt | cut -d \| -f 1 | grep -qw urlshortener; then
    echo "Database 'urlshortener' exists."
else
    echo "Failed to create database 'urlshortener'."
    exit 1
fi

# List tables in the database
echo "Tables in the database:"
psql -h localhost -p 5432 -U postgres -d urlshortener -c "\dt"

# Check if urls table exists
if psql -h localhost -p 5432 -U postgres -d urlshortener -c "\dt urls" | grep -q urls; then
    echo "Table 'urls' exists in the database."
    # Show table structure
    echo "Structure of 'urls' table:"
    psql -h localhost -p 5432 -U postgres -d urlshortener -c "\d urls"
    # Count records
    echo "Number of records in 'urls' table:"
    psql -h localhost -p 5432 -U postgres -d urlshortener -c "SELECT COUNT(*) FROM urls;"
else
    echo "Table 'urls' does not exist in the database."
fi

echo "Script completed successfully."
