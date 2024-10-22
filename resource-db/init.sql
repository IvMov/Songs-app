\c resource_service_db

CREATE TABLE IF NOT EXISTS resources (
    id SERIAL PRIMARY KEY,
    data BYTEA
);

INSERT INTO resources (data)
VALUES (E'\\x48656c6c6f20576f726c6421');