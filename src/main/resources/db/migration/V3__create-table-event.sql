CREATE TABLE event (
                       id int  NOT NULl AUTO_INCREMENT,
                       title VARCHAR(100) NOT NULL,
                       description VARCHAR(250) NOT NULL,
                       img_url VARCHAR(100) NULL,
                       event_url VARCHAR(100) NULL,
                       date_inicio TIMESTAMP NOT NULL,
                       date_fim TIMESTAMP NOT NULL,
                       remote BOOLEAN NOT NULL,
                       primary key (id)
);