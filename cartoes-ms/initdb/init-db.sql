CREATE TABLE IF NOT EXISTS tb_cliente (
      id SERIAL PRIMARY KEY,
      nome VARCHAR(255) NOT NULL,
      cpf VARCHAR(14) NOT NULL,
      email VARCHAR(255) NOT NULL,
      data_nascimento DATE NOT NULL,
      endereco VARCHAR(255),
      renda_mensal DOUBLE PRECISION
);


CREATE TABLE IF NOT EXISTS tb_cartao (
     id SERIAL PRIMARY KEY,
     numero_cartao VARCHAR(20) NOT NULL,
     nome_titular VARCHAR(255) NOT NULL,
     data_validade DATE NOT NULL,
     cvv VARCHAR(3) NOT NULL,
     ativo BOOLEAN NOT NULL,
     data_emissao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
     cliente_id BIGINT NOT NULL,
     CONSTRAINT FK_cartao_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente (id)
);


CREATE TABLE IF NOT EXISTS tb_proposta (
   id SERIAL PRIMARY KEY,
   status VARCHAR(255) NOT NULL,
   limite_credito VARCHAR(20) NOT NULL,
   data_proposta DATE NOT NULL,
   cliente_id BIGINT NOT NULL,
   CONSTRAINT FK_proposta_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente (id)
);

--- Inserts para tb_cliente
INSERT INTO tb_cliente (nome, cpf, email, data_nascimento, endereco, renda_mensal)
VALUES
    ('Ana Silva', '12345678901', 'ana.silva@example.com', '1990-05-15', 'Rua das Flores, 100', 2800),
    ('Bruno Martins', '23456789012', 'bruno.martins@example.com', '1985-08-25', 'Av. Brasil, 250', 6500),
    ('Carla Dias', '34567890123', 'carla.dias@example.com', '1992-12-30', 'Rua do Sol, 75', 7000);

--- Inserts para tb_cartao
INSERT INTO tb_cartao (numero_cartao, nome_titular, data_validade, cvv, ativo, data_emissao, cliente_id)
VALUES
    ('1111222233334444', 'Ana Silva', '2026-01-31', '123', TRUE, '2024-03-08 10:00:00', 1),
    ('5555666677778888', 'Bruno Martins', '2027-02-28', '456', TRUE, '2024-03-09 11:30:00', 2),
    ('9999000011112222', 'Carla Dias', '2025-03-31', '789', TRUE, '2024-03-10 09:45:00', 3);

--- Inserts para tb_proposta
INSERT INTO tb_proposta (status, limite_credito, data_proposta, cliente_id)
VALUES
    ('APROVADA', '12000', '2024-03-11', 1),
    ('REJEITADA', '0', '2024-03-12', 2),
    ('APROVADA', '15000', '2024-03-13', 3);