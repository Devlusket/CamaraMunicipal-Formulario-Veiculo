  CREATE TABLE veiculos (
      id         BIGSERIAL PRIMARY KEY,
      nome       VARCHAR(100) NOT NULL,
      placa      VARCHAR(10)  NOT NULL UNIQUE,
      ativo      BOOLEAN      NOT NULL DEFAULT TRUE,
      created_at TIMESTAMP    NOT NULL DEFAULT NOW()
  );