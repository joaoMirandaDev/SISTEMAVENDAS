CREATE TABLE roles (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE usuario
ADD COLUMN role INTEGER;

ALTER TABLE usuario
ADD FOREIGN KEY (role) REFERENCES roles(id);

INSERT into roles (name) VALUES ("ADMIN"),
    ("PROPRIETARIO"),
    ("GERENTE"),
    ("CAIXA")



