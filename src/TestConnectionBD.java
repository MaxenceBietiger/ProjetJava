import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionBD {
    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", "root", "25030378")) {
            System.out.println("Connexion réussie!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Échec de la connexion.");
        }
    }
}