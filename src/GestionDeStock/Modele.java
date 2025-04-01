package GestionDeStock;


import java.util.ArrayList;
import java.util.Objects;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Modele {

    int idRapport;
    String dateRapport;
    String contenuRapport;
    ArrayList<Modele> listeRapports = new ArrayList<Modele>();

    //Constructeur de la classe Rapports
    public Modele(int idRapport, String dateRapport, String contenuRapport) {
        this.idRapport = idRapport;
        this.dateRapport = dateRapport;
        this.contenuRapport = contenuRapport;
    }

    // Getters et Setters
    public int getIdRapport() {
        return idRapport;
    }

    public void setIdRapport(int idRapport) {
        this.idRapport = idRapport;
    }

    public String getDateRapport() {
        return dateRapport;
    }

    public void setDateRapport(String dateRapport) {
        this.dateRapport = dateRapport;
    }

    public String getContenuRapport() {
        return contenuRapport;
    }

    public void setContenuRapport(String contenuRapport) {
        this.contenuRapport = contenuRapport;
    }


    //Méthode pour afficher les rapports dans la liste des rapports
    public void afficherRapports() {
        for (Modele r : listeRapports) {
            System.out.println("ID: " + r.idRapport + " Date: " + r.dateRapport + " Contenu: " + r.contenuRapport);
        }
    }

    //Méthode pour ajouter un rapport
    public void ajouterRapport(Modele rapport) {
            listeRapports.add(rapport);
        }

        //Méthode pour supprimer un rapport
        public void supprimerRapport(Modele rapport) {
            listeRapports.remove(rapport);
        }

        //Méthode pour rechercher un rapport
        public void rechercherRapport(int idRapport) {
            for (Modele r : listeRapports) {
                if (Objects.equals(r.idRapport, idRapport)) {
                    System.out.println("ID: " + r.idRapport + " Date: " + r.dateRapport + " Contenu: " + r.contenuRapport);
                }
            }
        }
}
