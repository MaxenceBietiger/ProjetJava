package GestionDeStock;

import java.sql.*;
import java.util.ArrayList;

public class DBRapports {

    DatabaseMetaData DatabaseConnection;

    public ArrayList<ModeleProduits> genererRapportStock() {
        ArrayList<ModeleProduits> produits = new ArrayList<>();
        String sql = "SELECT * FROM produits";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ModeleProduits produit = new ModeleProduits(
                        rs.getInt("idProduit"),
                        rs.getString("nomProduit"),
                        rs.getString("descriptionProduit"),
                        rs.getDouble("prixProduit"),
                        rs.getInt("quantite"),
                        rs.getString("idFournisseur")
                );
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    public ArrayList<ModeleVente> genererRapportVentes(Date dateDebut, Date dateFin) {
        ArrayList<ModeleVente> ventes = new ArrayList<>();
        String sql = "SELECT * FROM vente WHERE dateVente BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, new java.sql.Date(dateDebut.getTime()));
            pstmt.setDate(2, new java.sql.Date(dateFin.getTime()));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ModeleVente vente = new ModeleVente(
                            rs.getInt("idVente"),
                            rs.getString("dateVente"),
                            rs.getDouble("montantVente"),
                            rs.getInt("idProduit")
                    );
                    ventes.add(vente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }
}
