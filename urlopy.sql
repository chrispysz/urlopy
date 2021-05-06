DROP TABLE IF EXISTS accounts_roles CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS vacations CASCADE;
DROP TABLE IF EXISTS edited_vacations CASCADE;
DROP TABLE IF EXISTS removed_vacations CASCADE;
DROP OWNED BY test_admin;
DROP USER IF EXISTS test_admin;
DROP USER IF EXISTS test_worker;
DROP USER IF EXISTS test_manager;

CREATE TABLE IF NOT EXISTS accounts(
                                       user_id serial PRIMARY KEY,
                                       username VARCHAR ( 50 ) UNIQUE NOT NULL,
                                       password VARCHAR ( 50 ) NOT NULL,
                                       email VARCHAR ( 255 ) UNIQUE NOT NULL,
                                       remaining_days_off INT DEFAULT 26

);

CREATE TABLE IF NOT EXISTS roles(
                                    role_id serial PRIMARY KEY,
                                    role_name VARCHAR (255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts_roles (
                                              user_id INT NOT NULL,
                                              role_id INT NOT NULL,
                                              PRIMARY KEY (user_id, role_id),
                                              FOREIGN KEY (role_id)
                                                  REFERENCES roles (role_id),
                                              FOREIGN KEY (user_id)
                                                  REFERENCES accounts (user_id)
);

CREATE TABLE IF NOT EXISTS vacations (
                                         vacation_id serial PRIMARY KEY,
                                         user_id INT NOT NULL,
                                         start_date DATE NOT NULL,
                                         end_date DATE NOT NULL,
                                         accepted BOOLEAN DEFAULT FALSE,
                                         FOREIGN KEY (user_id)
                                             REFERENCES accounts(user_id)
);

CREATE TABLE IF NOT EXISTS edited_vacations (
                                                vacation_id INT UNIQUE NOT NULL,
                                                user_id INT NOT NULL,
                                                start_date DATE NOT NULL,
                                                end_date DATE NOT NULL,
                                                accepted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS removed_vacations (
                                                 vacation_id INT UNIQUE NOT NULL,
                                                 user_id INT NOT NULL,
                                                 start_date DATE NOT NULL,
                                                 end_date DATE NOT NULL,
                                                 accepted BOOLEAN DEFAULT FALSE
);

INSERT INTO roles (role_name)
VALUES('admin');

INSERT INTO roles (role_name)
VALUES('manager');

INSERT INTO roles (role_name)
VALUES('worker');

INSERT INTO accounts (username, password, email)
VALUES ('test_worker','pass','test_worker@gmail.com');

CREATE USER test_worker WITH PASSWORD 'pass';
GRANT USAGE, SELECT ON SEQUENCE vacations_vacation_id_seq TO test_worker;
GRANT SELECT ON vacations TO test_worker;
GRANT SELECT ON accounts TO test_worker;
GRANT INSERT ON vacations TO test_worker;
GRANT INSERT ON edited_vacations TO test_worker;
GRANT INSERT ON removed_vacations TO test_worker;
GRANT DELETE ON vacations TO test_worker;
GRANT UPDATE ON accounts TO test_worker;

INSERT INTO accounts_roles (user_id, role_id)
VALUES (1,3);

INSERT INTO accounts (username, password, email)
VALUES ('test_manager','pass','test_manager@gmail.com');

CREATE USER test_manager WITH PASSWORD 'pass';
GRANT SELECT ON vacations TO test_manager;
GRANT UPDATE ON vacations TO test_manager;

INSERT INTO accounts_roles (user_id, role_id)
VALUES (2,2);

INSERT INTO accounts (username, password, email)
VALUES ('test_admin','pass','test_admin@gmail.com');

CREATE USER test_admin WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON DATABASE urlopy TO test_admin;
ALTER USER test_admin SUPERUSER;
-- GRANT ALL PRIVILEGES ON vacations TO test_admin;
-- GRANT ALL PRIVILEGES ON edited_vacations TO test_admin;
-- GRANT ALL PRIVILEGES ON removed_vacations TO test_admin;

INSERT INTO accounts_roles (user_id, role_id)
VALUES (3,1);
