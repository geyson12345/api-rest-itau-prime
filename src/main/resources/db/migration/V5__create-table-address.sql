CREATE TABLE address (
                         id int  NOT NULl AUTO_INCREMENT,
                         city VARCHAR(100) NOT NULL,
                         uf VARCHAR(100) NOT NULL,
                         event_id int,
                         primary key (id),
                         FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);