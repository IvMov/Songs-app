\c song_service_db

CREATE TABLE IF NOT EXISTS songs (
    id SERIAL PRIMARY KEY,
    resource_id BIGINT,
    metadata JSON
);

INSERT INTO songs (resource_id, metadata)
VALUES (1, '{"test_key_1":"value1","test_key_2":"value2"}');