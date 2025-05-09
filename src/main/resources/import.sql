CREATE TABLE student (
    id SERIAL BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance NUMERIC(15, 2) DEFAULT 0
);

CREATE OR REPLACE PROCEDURE tambah_student(
    p_name VARCHAR,
    p_balance NUMERIC
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO student(name, balance)
    VALUES (p_name, p_balance);
END;
$$;

CREATE OR REPLACE FUNCTION fungsi_tambah_student(
    p_name VARCHAR,
    p_balance NUMERIC
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    new_id INTEGER;
BEGIN
    INSERT INTO student(name, balance)
    VALUES (p_name, p_balance)
    RETURNING id INTO new_id;

    RETURN new_id;
END;
$$;

INSERT INTO student (name, balance) VALUES
('Alice', 1000.00),
('Bob', 750.50),
('Charlie', 0.00);
