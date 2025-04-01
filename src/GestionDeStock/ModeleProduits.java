package GestionDeStock;

import java.util.ArrayList;

public class ModeleProduits {
    public int idProduit;
    public String nomProduit;
    public String descriptionProduit;
    public double prixProduit;
    public int quantite;  // Nouveau champ pour la quantité en stock
    public String idFournisseur;
    ArrayList<ModeleProduits> listeProduits = new ArrayList<ModeleProduits>();

    public ModeleProduits(int idProduit, String nomProduit, String descriptionProduit, double prixProduit, int quantite, String idFournisseur) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.descriptionProduit = descriptionProduit;
        this.prixProduit = prixProduit;
        this.quantite = quantite;
        this.idFournisseur = idFournisseur;
    }

    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescriptionProduit() {
        return descriptionProduit;
    }

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    public double getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(String idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public void afficherProduits() {
        for (ModeleProduits p : listeProduits) {
            System.out.println("ID: " + p.idProduit + " Nom: " + p.nomProduit + " Description: " + p.descriptionProduit + " Prix: " + p.prixProduit + " Quantité: " + p.quantite + " Fournisseur ID: " + p.idFournisseur);
        }
    }

    public void ajouterProduit(ModeleProduits produit) {
        listeProduits.add(produit);
    }

    public void supprimerProduit(ModeleProduits produit) {
        listeProduits.remove(produit);
    }

    public void modifierProduit(ModeleProduits produit) {
        for (ModeleProduits p : listeProduits) {
            if (p.idProduit == produit.idProduit) {
                p.nomProduit = produit.nomProduit;
                p.descriptionProduit = produit.descriptionProduit;
                p.prixProduit = produit.prixProduit;
                p.quantite = produit.quantite;
                p.idFournisseur = produit.idFournisseur;
            }
        }
    }
}
