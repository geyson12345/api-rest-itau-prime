create table tb_user (
                         id int  NOT NULl AUTO_INCREMENT,
                         cpf varchar(255),
                         email varchar(255),
                         nome varchar(255),
                         senha varchar(255),
                         primary key (id,email)
);


INSERT INTO tb_user(nome, email,senha,cpf) VALUES ('Maria', 'maria@gmail.com','12345','1234567894578');
INSERT INTO tb_user(nome, email,senha,cpf) VALUES ('Bob', 'bob@gmail.com','2504','1234567894222');
INSERT INTO tb_user(nome, email,senha,cpf) VALUES ('Alex', 'alex@gmail.com','7845','1234567894444');

