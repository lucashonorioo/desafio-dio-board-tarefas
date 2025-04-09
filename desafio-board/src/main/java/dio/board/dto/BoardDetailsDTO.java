package dio.board.dto;

import java.util.List;

public record BoardDetailsDTO(Long id,
                              String nome,
                              List<BoardColumnDTO> columns) {
}
