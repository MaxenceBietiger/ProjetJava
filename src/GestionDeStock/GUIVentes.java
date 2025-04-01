package GestionDeStock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIVentes extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private DBVente venteDB;

    public GUIVentes() {
        setLayout(new BorderLayout());

        // Initialiser le DAO
        venteDB = new DBVente();

        // Créer le modèle de tableau
        tableModel = new DefaultTableModel(new Object[]{"ID", "Date", "Montant", "Produit ID"}, 0);
        table = new JTable(tableModel);

        // Ajouter les ventes au modèle de tableau
        ArrayList<ModeleVente> ventes = venteDB.consulterVentes();
        for (ModeleVente vente : ventes) {
            tableModel.addRow(new Object[]{vente.getIdVente(), vente.getDateVente(), vente.getMontantVente(), vente.getIdProduit()});
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
        add(panel, BorderLayout.CENTER);

        // Ajouter des boutons pour ajouter, modifier et supprimer des ventes
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajouterVente();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifierVente();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    private void ajouterVente() {
        String date = JOptionPane.showInputDialog("Date de la vente (YYYY-MM-DD) :");
        String montantStr = JOptionPane.showInputDialog("Montant de la vente :");
        String produitIdStr = JOptionPane.showInputDialog("ID du produit :");

        if (date != null && !date.trim().isEmpty() && montantStr != null && !montantStr.trim().isEmpty() && produitIdStr != null && !produitIdStr.trim().isEmpty()) {
            try {
                double montant = Double.parseDouble(montantStr);
                int produitId = Integer.parseInt(produitIdStr);
                ModeleVente nouvelleVente = new ModeleVente(0, date, montant, produitId);
                venteDB.ajouterVente(nouvelleVente);
                tableModel.addRow(new Object[]{0, date, montant, produitId});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierVente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String date = JOptionPane.showInputDialog("Nouvelle date de la vente (YYYY-MM-DD) :", tableModel.getValueAt(selectedRow, 1));
            String montantStr = JOptionPane.showInputDialog("Nouveau montant de la vente :", tableModel.getValueAt(selectedRow, 2));
            String produitIdStr = JOptionPane.showInputDialog("Nouvel ID du produit :", tableModel.getValueAt(selectedRow, 3));

            if (date != null && !date.trim().isEmpty() && montantStr != null && !montantStr.trim().isEmpty() && produitIdStr != null && !produitIdStr.trim().isEmpty()) {
                try {
                    double montant = Double.parseDouble(montantStr);
                    int produitId = Integer.parseInt(produitIdStr);
                    ModeleVente venteModifiee = new ModeleVente(id, date, montant, produitId);
                    venteDB.modifierVente(venteModifiee);
                    tableModel.setValueAt(date, selectedRow, 1);
                    tableModel.setValueAt(montant, selectedRow, 2);
                    tableModel.setValueAt(produitId, selectedRow, 3);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
