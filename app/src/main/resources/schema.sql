DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          title VARCHAR(255) NOT NULL,
                          price INTEGER
);