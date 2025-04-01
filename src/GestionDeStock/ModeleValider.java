package GestionDeStock;

public class ModeleValider {

    public boolean verifierProduit(ModeleProduits produit) {
        if (produit.nomProduit == null || produit.nomProduit.trim().isEmpty()) {
            System.out.println("Le nom du produit ne peut pas être vide.");
            return false;
        }
        if (produit.prixProduit <= 0) {
            System.out.println("Le prix du produit doit être un nombre positif.");
            return false;
        }
        return true;
    }

    public boolean verifierFournisseur(ModeleFournisseurs fournisseur) {
        if (fournisseur.nomFournisseur == null || fournisseur.nomFournisseur.trim().isEmpty()) {
            System.out.println("Le nom du fournisseur ne peut pas être vide.");
            return false;
        }
        return true;
    }

    public boolean validerVente(ModeleVente vente) {
        if (vente.dateVente == null || vente.dateVente.trim().isEmpty()) {
            System.out.println("La date de la vente ne peut pas être vide.");
            return false;
        }
        if (vente.montantVente <= 0) {
            System.out.println("Le montant de la vente doit être un nombre positif.");
            return false;
        }
        return true;
    }

    public boolean validerRapports(Modele rapports) {
        if (rapports.dateRapport == null || rapports.dateRapport.trim().isEmpty()) {
            System.out.println("La date du rapport ne peut pas être vide.");
            return false;
        }
        return true;
    }
}
