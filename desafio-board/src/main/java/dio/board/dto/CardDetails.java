package dio.board.dto;

import java.time.OffsetDateTime;

public record CardDetails(Long id,
                          String titulo,
                          String descricao,
                          boolean bloqueio,
                          OffsetDateTime criacao_bloqueio,
                          String descricao_bloqueio,
                          int valorBloqueio,
                          Long columnId,
                          String columnNome) {
}

