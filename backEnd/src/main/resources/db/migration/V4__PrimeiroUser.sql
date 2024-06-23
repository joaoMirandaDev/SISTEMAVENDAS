INSERT INTO colaborador
(nome, sobrenome, data_nascimento, cpf, rg, is_usuario, email, telefone, ativo)
VALUES('Joao victor', NULL, NULL, '13226726609', NULL, NULL, NULL, NULL, NULL);

INSERT INTO usuario
(senha, login, data_cadastro, colaborador_id, role)
VALUES('$2a$10$IAbOgyrcbEUgEHjrC4wWVuYgQcWFK7Hc/n2uIQ8HYGwVgfU6UETh6', '13226726609', '2024-01-20 15:38:39', 1, 1);