DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
                       urls BIGINT GENERATED ALWAYS AS IDENTITY,
                       title VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP
);