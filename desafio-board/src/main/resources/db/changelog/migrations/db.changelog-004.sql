--liquibase formatted sql
--changeset lucas:001
--comment: blocks table create

CREATE TABLE BLOCKS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    criacao_bloqueio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descricao_bloqueio VARCHAR(255) NOT NULL,
    final_bloqueio TIMESTAMP NULL,
    descricao_desbloqueio VARCHAR(255) NOT NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT cards__blocks_fk FOREIGN KEY(card_id) REFERENCES CARDS(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE BLOCKS