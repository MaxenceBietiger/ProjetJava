package GestionDeStock;

import java.util.ArrayList;
import java.util.Objects;
import java.sql.*;

public class ModeleFournisseurs {
    public String nomFournisseur;
    public String idFournisseur;
    ArrayList<ModeleFournisseurs> listeFournisseurs = new ArrayList<ModeleFournisseurs>();

    //Constructeur de la classe Fournisseurs
    public ModeleFournisseurs(String nomFournisseur, String numeroFournisseur) {
        this.nomFournisseur = nomFournisseur;
        this.idFournisseur = numeroFournisseur;
    }

    // Getters et Setters
    public String getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(String idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    //Méthode pour afficher les fournisseurs dans la liste des fournisseurs
    public void afficherFournisseurs() {
        for (ModeleFournisseurs f : listeFournisseurs) {
            System.out.println("Nom: " + f.nomFournisseur + " Numéro: " + f.idFournisseur);
        }
    }

    //Méthode pour modifier un fournisseur
    public void mofierFournisseur(ModeleFournisseurs fournisseur) {
        for (ModeleFournisseurs f : listeFournisseurs) {
            if (Objects.equals(f.nomFournisseur, fournisseur.nomFournisseur)) {
                f.idFournisseur = fournisseur.idFournisseur;
            }
        }
    }

    //Méthode pour ajouter un fournisseurs
    public void ajouterFournisseur(ModeleFournisseurs fournisseur) {
        listeFournisseurs.add(fournisseur);
    }

    //Méthode pour supprimer un fournisseur
    public void supprimerFournisseur(ModeleFournisseurs fournisseur) {
        listeFournisseurs.remove(fournisseur);
    }
}
