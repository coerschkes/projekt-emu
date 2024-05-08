package business.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private Connection connection;

    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    public void connect()
            throws ClassNotFoundException, SQLException {
//		todo: add right dependency here
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/emu?"
                        + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC",
                "root", "root");
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
