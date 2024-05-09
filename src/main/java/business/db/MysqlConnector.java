package business.db;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.sql.*;
import java.util.function.Function;

@SuppressWarnings("SqlSourceToSinkFlow")
class MysqlConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlConnector.class);
    private Connection connection;

    <T> T executeQuery(final String query, final Function<ResultSet, T> resultTransformer) throws SQLException, ClassNotFoundException {
        LOGGER.debug("Executing query: " + query);
        this.connect();
        try (final Statement statement = this.connection.createStatement()) {
            return resultTransformer.apply(statement.executeQuery(query));
        } catch (SQLException e) {
            LOGGER.error("Exception when executing query '" + query + "':", e);
            throw e;
        } finally {
            this.closeConnection();
        }
    }

    void executeUpdate(final String query) throws SQLException, ClassNotFoundException {
        LOGGER.debug("Executing update query: " + query);
        this.connect();
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("Exception when executing update query '" + query + "':", e);
            throw e;
        } finally {
            this.closeConnection();
        }
    }

    private void connect() throws ClassNotFoundException, SQLException {
        LOGGER.debug("Connecting to database...");
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/emu?"
                        + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC",
                "root", "root");
    }

    private void closeConnection() throws SQLException {
        LOGGER.debug("Closing connection...");
        this.connection.close();
    }
}
