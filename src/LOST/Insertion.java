package LOST;

import java.sql.*;

public class Insertion {

    // Méthode pour insérer une personne qui est venu signalé dans la base de donnée dans la base de données
    public static int insertReporter(String name, String email, String phone) {
        Connection conn = DatabaseConnection.getConnection(); // Connexion à la base de données
        if (conn == null) {
            return -1; // Retourner -1 si la connexion échoue
        }

        try {
            // on verifie si la personne existe   déjà avec son email
            String checkSql = "SELECT id FROM reporters WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // s'il existe, on retourne son ID
            }

            // Sinon, on l'enregistre dans la bd
            String insertSql = "INSERT INTO reporters (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, phone);
            int affectedRows = insertStmt.executeUpdate();

            if (affectedRows > 0) {
                // on recupère l'ID de la personne qu'on vient d'enregistrer
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // on retourne l'ID enregistré
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Retourner -1 en cas d'échec
    }

    // Méthode pour insérer un objet signalé dans la base de données
    public static void insertSignaledItem(Lost_items item, int reporterId) {
        Connection conn = DatabaseConnection.getConnection(); // Connexion à la base de données
        if (conn == null) {
            return; // Retourner si la connexion échoue
        }

        try {
            String insertSql = "INSERT INTO signaled_items (name, serial_number, description, date_reported, id_reporter) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, item.getName());
            insertStmt.setString(2, item.getSerialNumber());
            insertStmt.setString(3, item.getDescription());
            insertStmt.setDate(4, item.getDateReported());
            insertStmt.setInt(5, reporterId); // Utiliser l'ID de la personne qui a signalé qui a été recuperé plus haut

            int affectedRows = insertStmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Objet signalé ajouté avec succès.");
            } else {
                System.out.println("Erreur lors de l'ajout de l'objet signalé.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
