package dio.board.persistence.entity;

import lombok.Data;

@Data
public class BoardColumnEntity {

    private Long id;
    private String nome;
    private int ordem;
    private TipoColumnEnum tipo;
}
