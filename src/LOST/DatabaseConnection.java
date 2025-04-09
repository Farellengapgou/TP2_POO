package LOST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/LOST_TOOLS";
    private static final String USER = "root";
    private static final String PASSWORD = "ngtsfa50";

    public static Connection getConnection() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Driver JDBC non trouvé.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur : Échec de la connexion à la base de données.");
            e.printStackTrace();
        }
        return conn;
    }
}
