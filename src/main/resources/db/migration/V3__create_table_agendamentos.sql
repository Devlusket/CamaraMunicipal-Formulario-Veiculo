  CREATE TABLE agendamentos (
      id           BIGSERIAL PRIMARY KEY,
      requisitante VARCHAR(150) NOT NULL,
      cargo        VARCHAR(100) NOT NULL,
      veiculo_id   BIGINT       NOT NULL REFERENCES veiculos(id),
      data_inicio  TIMESTAMP    NOT NULL,
      data_fim     TIMESTAMP    NOT NULL,
      status       VARCHAR(20)  NOT NULL DEFAULT 'ATIVO',
      created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
  );