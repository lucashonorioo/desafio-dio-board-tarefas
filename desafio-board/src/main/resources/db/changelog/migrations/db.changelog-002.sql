--liquibase formatted sql
--changeset lucas:001
--comment: boards_columns table create

CREATE TABLE BOARDS_COLUMNS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    `ordem` int NOT NULL,
    tipo VARCHAR(7) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT boards__boards_columns_fk FOREIGN KEY(board_id) REFERENCES BOARDS(id) ON DELETE CASCADE,
    CONSTRAINT id_ordem_uk UNIQUE KEY unique_board_id_ordem (board_id, `ordem`)

) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS_COLUMNS