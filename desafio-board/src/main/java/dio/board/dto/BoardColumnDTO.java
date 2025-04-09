package dio.board.dto;

import dio.board.persistence.entity.TipoColumnEnum;

public record BoardColumnDTO(Long id,
                             String nome,
                             TipoColumnEnum tipo,
                             int cardsAmount) {
}
