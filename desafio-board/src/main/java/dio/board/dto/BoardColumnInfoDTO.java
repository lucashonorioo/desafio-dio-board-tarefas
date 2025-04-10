package dio.board.dto;

import dio.board.persistence.entity.TipoColumnEnum;

public record BoardColumnInfoDTO(Long id, int ordem, TipoColumnEnum tipo) {
}
