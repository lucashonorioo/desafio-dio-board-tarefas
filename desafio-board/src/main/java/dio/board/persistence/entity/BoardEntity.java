package dio.board.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Data
public class BoardEntity {

    private Long id;
    private String nome;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardColumnEntity getInitialColumn(){
        return boardColumns.stream().
                filter(bc -> bc.getTipo().equals(TipoColumnEnum.INICIAL))
                .findFirst().orElseThrow();
    }

    public BoardColumnEntity getCancelColumn(){
        return getFilteredColumn(bc -> bc.getTipo().equals(TipoColumnEnum.CANCELADO));
    }

    private BoardColumnEntity getFilteredColumn(Predicate<BoardColumnEntity> filter){
        return boardColumns.stream()
                .filter(filter)
                .findFirst().orElseThrow();
    }

}
