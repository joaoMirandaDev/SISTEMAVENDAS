CREATE TABLE colaborador (
  id INTEGER NOT NULL AUTO_INCREMENT,
  nome TEXT NOT NULL,
  sexo VARCHAR(50),
  sobrenome VARCHAR(100),
  data_nascimento DATE,
  cpf VARCHAR(14) UNIQUE NOT NULL,
  data_contrato_inicial DATE,
  data_contrato_final DATE,
  rg VARCHAR(20),
  documento_id_photo INTEGER,
  salario DOUBLE,
  is_usuario INTEGER,
  email VARCHAR(100),
  telefone VARCHAR(100),
  ativo INTEGER,
  id_endereco INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY (id_endereco) REFERENCES endereco(id),
  FOREIGN KEY (documento_id_photo) REFERENCES documentos(id)
);

CREATE TABLE usuario (
  id INTEGER NOT NULL AUTO_INCREMENT,
  senha VARCHAR(255) NOT NULL,
  login VARCHAR(11) UNIQUE NOT NULL,
  data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  colaborador_id INTEGER,
  photo_perfil TEXT,
  FOREIGN KEY (colaborador_id) REFERENCES colaborador(id) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY (id)
);



