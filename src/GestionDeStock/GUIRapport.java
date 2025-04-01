package GestionDeStock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class GUIRapport extends JFrame {
    private DBRapports rapportDB;
    private JTable table;
    private DefaultTableModel tableModel;

    public GUIRapport() {
        setTitle("Rapports");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        rapportDB = new DBRapports();
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton stockButton = new JButton("Rapport de Stock");
        JButton ventesButton = new JButton("Rapport de Ventes");
        JButton exportButton = new JButton("Exporter en CSV");

        stockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afficherRapportStock();
            }
        });

        ventesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afficherRapportVentes();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exporterEnCSV();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(stockButton);
        buttonPanel.add(ventesButton);
        buttonPanel.add(exportButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void afficherRapportStock() {
        ArrayList<ModeleProduits> produits = rapportDB.genererRapportStock();
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Nom", "Description", "Prix", "Quantité", "Fournisseur ID"});
        tableModel.setRowCount(0);
        for (ModeleProduits produit : produits) {
            tableModel.addRow(new Object[]{produit.getIdProduit(), produit.getNomProduit(), produit.getDescriptionProduit(), produit.getPrixProduit(), produit.getQuantite(), produit.getIdFournisseur()});
        }
    }

    private void afficherRapportVentes() {
        Date dateDebut = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000);
        Date dateFin = new Date();
        ArrayList<ModeleVente> ventes = rapportDB.genererRapportVentes((java.sql.Date) dateDebut, (java.sql.Date) dateFin);
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Date", "Montant", "Produit ID"});
        tableModel.setRowCount(0);
        for (ModeleVente vente : ventes) {
            tableModel.addRow(new Object[]{vente.getIdVente(), vente.getDateVente(), vente.getMontantVente(), vente.getIdProduit()});
        }
    }

    private void exporterEnCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exporter en CSV");
        fileChooser.setSelectedFile(new File("rapport.csv"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    writer.write(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        writer.write(tableModel.getValueAt(i, j).toString());
                        if (j < tableModel.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write("\n");
                }
                JOptionPane.showMessageDialog(this, "Exportation réussie!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'exportation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIRapport gui = new GUIRapport();
            gui.setVisible(true);
        });
    }
}
