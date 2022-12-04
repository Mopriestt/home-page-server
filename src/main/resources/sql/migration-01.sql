CREATE DATABASE HOMEPAGE;

USE HOMEPAGE;

CREATE TABLE chat_threads(
	thread_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NULL,
	post_time DATETIME DEFAULT(UTC_TIMESTAMP) NOT NULL,
    quote_id INT NULL,
    message TEXT,
    PRIMARY KEY(thread_id)
);

CREATE TABLE connection_log(
	connection_id INT NOT NULL AUTO_INCREMENT,
	connection_time DATETIME DEFAULT(UTC_TIMESTAMP) NOT NULL,
    ip CHAR(15),
    country CHAR(2),
    region VARCHAR(255),
    city VARCHAR(100),
    latitude DECIMAL(8,6),
    longitude DECIMAL(9,6),
    postal VARCHAR(20),
    PRIMARY KEY(connection_id)
);