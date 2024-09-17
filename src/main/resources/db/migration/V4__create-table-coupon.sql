CREATE TABLE coupon (
                        id int  NOT NULl AUTO_INCREMENT,
                        code VARCHAR(100) NOT NULL,
                        discount INTEGER NOT NULL,
                        valid TIMESTAMP NOT NULL,
                        event_id int,
                        primary key (id),
                        FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);