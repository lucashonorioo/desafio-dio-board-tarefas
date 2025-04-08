package dio.board.persistence.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class BlockEntity {

    private Long id;
    private OffsetDateTime criacaoBloqueio;
    private String descricaoBloqueio;
    private OffsetDateTime criacaoDesbloqueio;
    private String descricaoDesbloqueio;
}
