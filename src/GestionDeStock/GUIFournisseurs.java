package GestionDeStock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class GUIFournisseurs extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private DBFournisseurs fournisseursBD;
    private DatabaseMetaData DatabaseConnection;

    public GUIFournisseurs() {
        setTitle("Gestion des Fournisseurs");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialiser la connexion à la base de données
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connexion à la base de données réussie!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Initialiser le DAO
        fournisseursBD = new DBFournisseurs();

        // Créer le modèle de tableau
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom"}, 0);
        table = new JTable(tableModel);

        // Ajouter les fournisseurs au modèle de tableau
        ArrayList<ModeleFournisseurs> fournisseurs = fournisseursBD.consulterFournisseurs();
        for (ModeleFournisseurs fournisseur : fournisseurs) {
            tableModel.addRow(new Object[]{fournisseur.getIdFournisseur(), fournisseur.getNomFournisseur()});
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

        // Ajouter des boutons pour ajouter, modifier et supprimer des fournisseurs
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajouterFournisseur();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifierFournisseur();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerFournisseur();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    private void ajouterFournisseur() {
        String id = JOptionPane.showInputDialog("ID du fournisseur :");
        String nom = JOptionPane.showInputDialog("Nom du fournisseur :");
        if (id != null && !id.trim().isEmpty() && nom != null && !nom.trim().isEmpty()) {
            ModeleFournisseurs nouveauFournisseur = new ModeleFournisseurs(id, nom);
            fournisseursBD.ajouterFournisseur(nouveauFournisseur);
            tableModel.addRow(new Object[]{id, nom});
        }
    }

    private void modifierFournisseur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String nom = JOptionPane.showInputDialog("Nouveau nom du fournisseur :", tableModel.getValueAt(selectedRow, 1));
            if (nom != null && !nom.trim().isEmpty()) {
                ModeleFournisseurs fournisseurModifie = new ModeleFournisseurs(id, nom);
                fournisseursBD.modifierFournisseur(fournisseurModifie);
                tableModel.setValueAt(nom, selectedRow, 1);
            }
        }
    }

    private void supprimerFournisseur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            fournisseursBD.supprimerFournisseur(id);
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIFournisseurs gui = new GUIFournisseurs();
            gui.setVisible(true);
        });
    }
}
