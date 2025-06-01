/*
Database Connection Page - Alpha Team
Consists of Andres Melendez, Jeffrey Reid, Edgar Arroyo, Jordany Gonzalez, and Matthew Trinh

Purpose:
This class provides centralized methods for connecting to the MBay MySQL database.
- Loads credentials and connection properties from an external `db.properties` file.
- Provides a `getConnection()` method to connect to the MBay schema.
- Provides a `createDatabase()` method to connect without a schema (for initial DB creation).
 */

package MBayDB;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "/db.properties";
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;
    

    static {
        try (InputStream input = DBConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            prop.load(input);
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB config", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static Connection createDatabase() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", dbUser, dbPassword);
    }
}
