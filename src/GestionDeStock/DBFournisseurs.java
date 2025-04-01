package GestionDeStock;

import java.sql.*;
import java.util.ArrayList;

public class DBFournisseurs {

    DatabaseMetaData DatabaseConnection;
    
    public void ajouterFournisseur(ModeleFournisseurs fournisseur) {
        String sql = "INSERT INTO fournisseurs (idFournisseur, nomFournisseur) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fournisseur.idFournisseur);
            pstmt.setString(2, fournisseur.nomFournisseur);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierFournisseur(ModeleFournisseurs fournisseur) {
        String sql = "UPDATE fournisseurs SET nomFournisseur = ? WHERE idFournisseur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fournisseur.nomFournisseur);
            pstmt.setString(2, fournisseur.idFournisseur);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerFournisseur(String idFournisseur) {
        String sql = "DELETE FROM fournisseurs WHERE idFournisseur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idFournisseur);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModeleFournisseurs> consulterFournisseurs() {
        ArrayList<ModeleFournisseurs> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM fournisseurs";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ModeleFournisseurs fournisseur = new ModeleFournisseurs(
                        rs.getString("idFournisseur"),
                        rs.getString("nomFournisseur")
                );
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }
}
