-- -------------------------------
-- Instruments
-- -------------------------------
INSERT INTO instrument (symbol, name, isin) VALUES
                                                ('AAPL', 'Apple Inc.', 'US0378331005'),
                                                ('GOOGL', 'Alphabet Inc.', 'US02079K3059'),
                                                ('MSFT', 'Microsoft Corp.', 'US5949181045');

-- -------------------------------
-- Trades from Two Systems (Same trade_id to trigger reconciliation)
-- -------------------------------
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES
-- Matching trades
('T1001', 'AAPL', 150.00, 10, 'Bloomberg', '2024-06-15'),
('T1001', 'AAPL', 150.00, 10, 'Reuters', '2024-06-15'),

-- Price mismatch
('T1002', 'AAPL', 150.00, 10, 'Bloomberg', '2024-06-15'),
('T1002', 'AAPL', 188.00, 10, 'Reuters', '2024-06-15'),

-- Quantity mismatch
('T1003', 'GOOGL', 2800.00, 5, 'Bloomberg', '2024-06-15'),
('T1003', 'GOOGL', 2800.00, 6, 'Reuters', '2024-06-15'),

-- Both mismatch
('T1004', 'GOOGL', 2700.00, 4, 'Bloomberg', '2024-06-15'),
('T1004', 'GOOGL', 2800.00, 5, 'Reuters', '2024-06-15'),

-- Single-side trade (no pair)
('T1005', 'MSFT', 300.00, 10, 'Bloomberg', '2024-06-15');

-- Additional matching data
INSERT INTO trade (trade_id, instrument, price, quantity, source_system, trade_date) VALUES
                                                                                         ('T1006', 'MSFT', 320.00, 20, 'Bloomberg', '2024-06-15'),
                                                                                         ('T1006', 'MSFT', 320.00, 20, 'Reuters', '2024-06-15');

-- -------------------------------
-- Sample Reconciliation Run (optional, if manually inserting run)
-- -------------------------------
INSERT INTO reconciliation_run (run_date, status, matched_count, unmatched_count)
VALUES (NOW(), 'COMPLETED', 2, 3);

-- -------------------------------
-- Sample Differences (if not generated via service)
-- -------------------------------
INSERT INTO reconciliation_difference (trade_id, field_name, value_system_a, value_system_b, reconciliation_run_id) VALUES
                                                                                                                        ('T1002', 'price', '150.0', '148.0', 1),
                                                                                                                        ('T1003', 'quantity', '5', '6', 1),
                                                                                                                        ('T1004', 'price', '2700.0', '2800.0', 1),
                                                                                                                        ('T1004', 'quantity', '4', '5', 1);
