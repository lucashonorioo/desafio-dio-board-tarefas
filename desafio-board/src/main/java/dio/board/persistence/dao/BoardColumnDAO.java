package dio.board.persistence.dao;

import dio.board.persistence.entity.BoardColumnEntity;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RequiredArgsConstructor
public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException{
        var sql = "INSERT INTO BOARDS_COLUMNS (nome, `ordem`, tipo, board_id) VALUES (?, ?, ?, ?)";
        try(var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getNome());
            statement.setInt(2, entity.getOrdem());
            statement.setString(3, entity.getTipo().name());
            statement.setLong(4, entity.getBoard().getId());
            statement.executeUpdate();
            try(var generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(Long id) throws  SQLException{
        return null;
    }
}
