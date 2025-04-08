package dio.board.service;

import dio.board.persistence.dao.BoardColumnDAO;
import dio.board.persistence.dao.BoardDAO;
import dio.board.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumunDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);
        if(optional.isPresent()){
            var entity = optional.get();
            entity.setBoardColumns(boardColumunDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}
