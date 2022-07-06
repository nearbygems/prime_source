CREATE TABLE task
(
    id          BIGSERIAL PRIMARY KEY,
    date        DATE        NOT NULL,
    description TEXT,
    done        BOOLEAN     NOT NULL DEFAULT FALSE,
    username    VARCHAR(50) NOT NULL
);

CREATE INDEX task_date_idx ON task (date);

CREATE INDEX task_done_idx ON task (done);

CREATE TABLE users
(
    username VARCHAR(50) NOT NULL,
    password VARCHAR(68) NOT NULL,
    enabled  boolean     NOT NULL,
    PRIMARY KEY (USERNAME)
);

CREATE TABLE authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(68) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);