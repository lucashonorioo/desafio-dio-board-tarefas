package dio.board.persistence.dao;

import dio.board.dto.CardDetails;
import lombok.AllArgsConstructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;

import static dio.board.persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;
import static java.util.Objects.nonNull;



@AllArgsConstructor
public class CardDAO {

    private final Connection connection;

    public Optional<CardDetails> findById(final Long id) throws SQLException {
        var sql =
                """
                SELECT c.id,
                       c.titulo,
                       c.descricao,
                       b.criacao_bloqueio,
                       b.descricao_bloqueio,
                       c.board_column_id,
                       bc.nome,
                       (SELECT COUNT(sub_b.id)
                               FROM BLOCKS sub_b
                              WHERE sub_b.card_id = c.id) valor_bloqueio
                  FROM CARDS c
                  LEFT JOIN BLOCKS b
                    ON c.id = b.card_id
                   AND b.unblocked_at IS NULL
                 INNER JOIN BOARDS_COLUMNS bc
                    ON bc.id = c.board_column_id
                  WHERE c.id = ?;
                """;
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()){
                var dto = new CardDetails(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.titulo"),
                        resultSet.getString("c.descricao"),
                        nonNull(resultSet.getString("b.descricao_bloqueio")),
                        toOffsetDateTime(resultSet.getTimestamp("b.criacao_bloqueio")),
                        resultSet.getString("b.descricao_bloqueio"),
                        resultSet.getInt("valor_bloqueio"),
                        resultSet.getLong("c.board_column_id"),
                        resultSet.getString("bc.nome")
                );
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }


}
