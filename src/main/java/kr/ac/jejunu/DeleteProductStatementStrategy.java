package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteProductStatementStrategy implements StatementStrategy {
    private Long id;

    public DeleteProductStatementStrategy(Long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id=?");
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }
}
