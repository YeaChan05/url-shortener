-- Create the urls table if it doesn't exist
CREATE TABLE IF NOT EXISTS urls (
    short_key VARCHAR(10) PRIMARY KEY,
    original_url TEXT NOT NULL,
    created_at BIGINT NOT NULL
);

-- Create a unique index on short_key if it doesn't exist
CREATE UNIQUE INDEX IF NOT EXISTS urls_short_key_idx ON urls (short_key);

-- Display the table structure
\d urls

-- Count the number of rows in the table
SELECT COUNT(*) FROM urls;
