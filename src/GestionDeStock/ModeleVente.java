package GestionDeStock;

import java.util.ArrayList;

public class ModeleVente {
    int idVente;
    String dateVente;
    double montantVente;
    int idProduit;  // Nouveau champ pour l'ID du produit
    ArrayList<ModeleVente> listeVentes = new ArrayList<ModeleVente>();

    public ModeleVente(int idVente, String dateVente, double montantVente, int idProduit) {
        this.idVente = idVente;
        this.dateVente = dateVente;
        this.montantVente = montantVente;
        this.idProduit = idProduit;
    }

    // Getters et Setters
    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public String getDateVente() {
        return dateVente;
    }

    public void setDateVente(String dateVente) {
        this.dateVente = dateVente;
    }

    public double getMontantVente() {
        return montantVente;
    }

    public void setMontantVente(double montantVente) {
        this.montantVente = montantVente;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public void afficherVentes() {
        for (ModeleVente v : listeVentes) {
            System.out.println("ID: " + v.idVente + " Date: " + v.dateVente + " Montant: " + v.montantVente + " Produit ID: " + v.idProduit);
        }
    }

    public void ajouterVente(ModeleVente vente) {
        listeVentes.add(vente);
    }

    public void supprimerVente(ModeleVente vente) {
        listeVentes.remove(vente);
    }

    public void modifierVente(ModeleVente vente) {
        for (ModeleVente v : listeVentes) {
            if (v.idVente == vente.idVente) {
                v.dateVente = vente.dateVente;
                v.montantVente = vente.montantVente;
                v.idProduit = vente.idProduit;
            }
        }
    }
}
