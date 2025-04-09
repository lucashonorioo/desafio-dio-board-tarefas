package dio.board.persistence.dao;

import dio.board.dto.CardDetails;
import lombok.AllArgsConstructor;
import java.sql.Connection;


@AllArgsConstructor
public class CardDAO {

    private final Connection connection;

    public CardDetails findById(final Long id){
        return null;
    }


}
