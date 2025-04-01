package GestionDeStock;

import java.sql.*;
import java.util.ArrayList;

public class DBVente {

    private DatabaseMetaData DatabaseConnection;

    public void ajouterVente(ModeleVente vente) {
        if (!validerVente(vente)) {
            System.out.println("Données de la vente invalides.");
            return;
        }

        String sqlVente = "INSERT INTO vente (dateVente, montantVente, idProduit) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE produits SET quantite = quantite - 1 WHERE idProduits = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Désactiver l'autocommit pour s'assurer que les deux opérations réussissent ou échouent ensemble
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtVente = conn.prepareStatement(sqlVente);
                 PreparedStatement pstmtStock = conn.prepareStatement(sqlUpdateStock)) {

                // Enregistrer la vente
                pstmtVente.setString(1, vente.getDateVente());
                pstmtVente.setDouble(2, vente.getMontantVente());
                pstmtVente.setInt(3, vente.getIdProduit());
                pstmtVente.executeUpdate();

                // Mettre à jour la quantité en stock
                pstmtStock.setInt(1, vente.getIdProduit());
                pstmtStock.executeUpdate();

                // Valider la transaction
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierVente(ModeleVente vente) {
        if (!validerVente(vente)) {
            System.out.println("Données de la vente invalides.");
            return;
        }

        String sql = "UPDATE vente SET dateVente = ?, montantVente = ?, idProduit = ? WHERE idVente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vente.getDateVente());
            pstmt.setDouble(2, vente.getMontantVente());
            pstmt.setInt(3, vente.getIdProduit());
            pstmt.setInt(4, vente.getIdVente());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModeleVente> consulterVentes() {
        ArrayList<ModeleVente> ventes = new ArrayList<>();
        String sql = "SELECT * FROM vente";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ModeleVente vente = new ModeleVente(
                        rs.getInt("idVente"),
                        rs.getString("dateVente"),
                        rs.getDouble("montantVente"),
                        rs.getInt("idProduit")
                );
                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    private boolean validerVente(ModeleVente vente) {
        if (vente.getDateVente() == null || vente.getDateVente().trim().isEmpty()) {
            System.out.println("La date de la vente ne peut pas être vide.");
            return false;
        }
        if (vente.getMontantVente() <= 0) {
            System.out.println("Le montant de la vente doit être un nombre positif.");
            return false;
        }
        if (vente.getIdProduit() <= 0) {
            System.out.println("L'ID du produit doit être un entier positif.");
            return false;
        }
        return true;
    }
}
