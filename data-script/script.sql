DROP TABLE IF EXISTS price_data_table;

DROP TABLE IF EXISTS price_data_table;
CREATE TABLE price_data_table (
    entry_number SERIAL PRIMARY KEY,
    time_stamp VARCHAR(8)NOT NULL,  -- Stores time as string in "hh:mm AM/PM" format
    currency_pair VARCHAR(10) NOT NULL,
    price DECIMAL(10, 5)NOT NULL,
    volume INT NOT NULL
);
INSERT INTO price_data_table (time_stamp, currency_pair, price, volume) 
VALUES
    ('05:00 AM', 'AUD/USD', 0.566, 230),
    ('05:01 AM', 'NZD/JPY', 0.256, 457),
    ('05:02 AM', 'AUD/USD', 0.456, 234555),
    ('06:00 AM', 'NZD/JPY', 0.523, 34212),
    ('06:01 AM', 'NZD/USD', 0.124, 67655),
    ('10:01 AM', 'NZD/USD', 0.148, 6454),
    ('10:01 PM', 'NZD/USD', 0.458, 6230);
SELECT * from price_data_table;
