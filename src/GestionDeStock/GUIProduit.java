package GestionDeStock;

import GestionDeStock.Modele.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIProduit extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    public GUIProduit() throws SQLException {
        setTitle("Gestion des Produits");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialiser la connexion à la base de données
        //ArrayList<Produits> produits = new ArrayList<>();
        //String sql = "SELECT * FROM produits";

        //Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DB_Projet_Java", "root", "25030378");
        

        // Créer le modèle de tableau
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Prix"}, 0);
        table = new JTable(tableModel);

        // Ajouter les produits au modèle de tableau
        DBProduits produitDB = new DBProduits();
        ArrayList<ModeleProduits> produits1 = produitDB.consulterProduits();
        for (ModeleProduits produit : produits1) {
            tableModel.addRow(new Object[]{produit.getIdProduit(), produit.getNomProduit(), produit.getDescriptionProduit(), produit.getPrixProduit()});
        }

        // Ajouter la fonctionnalité de tri
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Ajouter la fonctionnalité de recherche
        searchField = new JTextField(20);
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Ajouter le tableau et le champ de recherche à la fenêtre
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(searchField, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIProduit gui = null;
			try {
				gui = new GUIProduit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            gui.setVisible(true);
        });
    }
}
