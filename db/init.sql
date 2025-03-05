CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    userName TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL

);
