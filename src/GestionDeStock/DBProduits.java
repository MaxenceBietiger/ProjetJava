package GestionDeStock;

import java.sql.*;
import java.util.ArrayList;

public class DBProduits {
    private DatabaseMetaData DatabaseConnection;
    
    

    public void ajouterProduit(ModeleProduits produit) {
        if (!validerProduit(produit)) {
            System.out.println("Données du produit invalides.");
            return;
        }

        String sql = "INSERT INTO produits (nomProduit, descriptionProduit, prixProduit, quantite, idFournisseur) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produit.nomProduit);
            pstmt.setString(2, produit.descriptionProduit);
            pstmt.setDouble(3, produit.prixProduit);
            pstmt.setInt(4, produit.quantite);
            pstmt.setString(5, produit.idFournisseur);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierProduit(ModeleProduits produit) {
        if (!validerProduit(produit)) {
            System.out.println("Données du produit invalides.");
            return;
        }

        String sql = "UPDATE produits SET nomProduit = ?, descriptionProduit = ?, prixProduit = ?, quantite = ?, idFournisseur = ? WHERE idProduits = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produit.nomProduit);
            pstmt.setString(2, produit.descriptionProduit);
            pstmt.setDouble(3, produit.prixProduit);
            pstmt.setInt(4, produit.quantite);
            pstmt.setString(5, produit.idFournisseur);
            pstmt.setInt(6, produit.idProduit);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModeleProduits> consulterProduits() {
        ArrayList<ModeleProduits> produits = new ArrayList<>();
        String sql = "SELECT * FROM produits";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DB_Projet_Java", "root", "25030378");
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

    private boolean validerProduit(ModeleProduits produit) {
        if (produit.nomProduit == null || produit.nomProduit.trim().isEmpty()) {
            System.out.println("Le nom du produit ne peut pas être vide.");
            return false;
        }
        if (produit.prixProduit <= 0) {
            System.out.println("Le prix du produit doit être un nombre positif.");
            return false;
        }
        if (produit.quantite < 0) {
            System.out.println("La quantité du produit ne peut pas être négative.");
            return false;
        }
        if (produit.idFournisseur == null || produit.idFournisseur.trim().isEmpty()) {
            System.out.println("L'ID du fournisseur ne peut pas être vide.");
            return false;
        }
        // Ajoutez d'autres validations si nécessaire
        return true;
    }
}
