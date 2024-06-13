CREATE TABLE documentos (
  id INTEGER NOT NULL AUTO_INCREMENT,
  usuario_id INTEGER,
  nome TEXT,
  route TEXT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE hibernate_sequence (
    next_val BIGINT
);
INSERT INTO hibernate_sequence VALUES(1);
