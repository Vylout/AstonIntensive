package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static final String URL_KAY = "db.url";
    private static final String USERNAME_KAY = "db.username";
    private static final String PASSWORD_KAY = "db.password";

    static {
        loadDriver();
    }

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KAY),
                    PropertiesUtil.get(USERNAME_KAY),
                    PropertiesUtil.get(PASSWORD_KAY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
