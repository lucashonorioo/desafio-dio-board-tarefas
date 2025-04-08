package dio.board.persistence.entity;

import java.util.stream.Stream;

public enum TipoColumnEnum {

    INICIAL,
    PENDENTE,
    FINAL,
    CANCELADO;

    public static TipoColumnEnum findByName(final String nome){
        return Stream.of(TipoColumnEnum.values())
                .filter(b -> b.name().equals(nome))
                .findFirst().orElseThrow();
    }

}
