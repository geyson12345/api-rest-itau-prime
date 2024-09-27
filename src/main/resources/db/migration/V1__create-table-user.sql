create table tb_user (
                         id int  NOT NULl AUTO_INCREMENT,
                         cpf varchar(255),
                         email varchar(255),
                         nome varchar(255),
                         senha varchar(255),
                         primary key (id,email)
);