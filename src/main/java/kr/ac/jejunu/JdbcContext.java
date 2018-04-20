package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    Product jdbcContextGet(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.getPreparedStatement(connection);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setTitle(resultSet.getString("title"));
                product.setPrice(resultSet.getInt("price"));
            }
        } finally {

            //자원을 해지한다.
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }


        return product;
    }

    Long jdbcContextInsert(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.getPreparedStatement(connection);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            id = resultSet.getLong(1);
        } finally {

            //자원을 해지한다.
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return id;
    }

    void jdbcContextUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.getPreparedStatement(connection);
            preparedStatement.executeUpdate();

        } finally {

            //자원을 해지한다.
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    Product queryForObject(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement;
        };
        return jdbcContextGet(statementStrategy);
    }

    Long insert(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement;
        };
        return jdbcContextInsert(statementStrategy);
    }

    void update(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement;
        };
        jdbcContextUpdate(statementStrategy);
    }
}