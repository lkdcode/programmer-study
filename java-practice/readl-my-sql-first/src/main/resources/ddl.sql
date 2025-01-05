DROP TABLE tb_member;

CREATE TABLE tb_member (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(10),
    phone VARCHAR(11),
    gender VARCHAR(1),
    team VARCHAR(10),
    PRIMARY KEY(id)
) ENGINE=InnoDB;