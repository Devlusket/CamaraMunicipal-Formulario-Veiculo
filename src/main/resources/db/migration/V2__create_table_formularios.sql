  CREATE TABLE formularios (
      id                   BIGSERIAL PRIMARY KEY,
      requisitante         VARCHAR(150) NOT NULL,
      cargo                VARCHAR(100) NOT NULL,
      veiculo_id           BIGINT       NOT NULL REFERENCES veiculos(id),
      data_saida           TIMESTAMP    NOT NULL,
      data_retorno_prevista TIMESTAMP   NOT NULL,
      itinerario           VARCHAR(300) NOT NULL,
      justificativa        TEXT         NOT NULL,
      odometro_saida       INTEGER      NOT NULL,
      observacao           TEXT,
      created_at           TIMESTAMP    NOT NULL DEFAULT NOW()
  );