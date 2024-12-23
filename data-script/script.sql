-- Drop the table if it exists
DROP TABLE IF EXISTS price_data_table;

-- Create the table with the new 'hour' column
CREATE TABLE price_data_table (
    entry_number SERIAL PRIMARY KEY,
    time_stamp VARCHAR(8) NOT NULL,  -- Stores time as string in "hh:mm AM/PM" format
    currency_pair VARCHAR(10) NOT NULL,
    price DECIMAL(10, 5) NOT NULL,
    volume INT NOT NULL,
    hour INT NOT NULL -- New column to store the extracted hour
);

-- Add an index on 'currency_pair' and 'hour' for optimized querying
CREATE INDEX idx_currency_pair_hour
ON price_data_table (currency_pair, hour);

-- setting the 'hour' column using Update query and the EXTRACT function
UPDATE price_data_table
SET hour = EXTRACT(HOUR FROM TO_TIMESTAMP(time_stamp, 'HH:MI AM'));


INSERT INTO price_data_table (time_stamp, currency_pair, price, volume, hour)
VALUES
    ('05:00 AM', 'AUD/USD', 0.566, 230, EXTRACT(HOUR FROM TO_TIMESTAMP('05:00 AM', 'HH:MI AM')));
    
