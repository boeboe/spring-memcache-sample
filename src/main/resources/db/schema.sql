DROP TABLE IF EXISTS person CASCADE;

CREATE TABLE person (
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR(63) NOT NULL,
  last_name  VARCHAR(63) NOT NULL,
  version    INT         NOT NULL DEFAULT 0,
  UNIQUE (first_name, last_name)
);

