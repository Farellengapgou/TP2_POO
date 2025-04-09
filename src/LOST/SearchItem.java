package LOST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchItem extends JDialog {


    private JTextField searchField;
    private JButton searchButton;
    private JList<String> resultsList;
    private DefaultListModel<String> listModel;

    public SearchItem(JFrame parent) {
        super(parent, "Rechercher un objet", true);
        setSize(1000, 1000);
        setLocationRelativeTo(parent);

        // Créer un panneau pour organiser les éléments
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // on va mettre un panneau en haut pour la zone de texte et le bouton rechercher après on va mettre un listModel au milieu pour afficher
        // les resultats de recherhe et en bas un texte indicatif , un bouton selectionner et un pour quitter
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Entrez le nom ou numéro de série:");
        searchPanel.add(label);
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchButton = new JButton("Rechercher");
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);
        JLabel notice = new JLabel("<html>Voici des appareils correspondant aux caractéristiques de ce que vous avez entré.<br>" +
                "Veuillez lire attentivement et choisir s'il y en a un qui correspond à l'appareil que vous comptez acheter.<br>" +
                "Nous procéderons directement en cas de sélection à un signalement à la personne qui a perdu cet appareil.</html>");


        // Liste des résultats
        listModel = new DefaultListModel<>();
        resultsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(resultsList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter un bouton pour fermer la fenêtre de dialogue
        JButton selectButton = new JButton("SELECTIONNER");
        selectButton.addActionListener(e -> handleResultSelection());

        // Ajouter un bouton pour quitter la recherche si aucun objet trouvé
        JButton exitButton = new JButton("Quitter");
        exitButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(notice);
        buttonPanel.add(selectButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);


        searchButton.addActionListener(e -> searchItems());

        // Fermer la fenêtre si l'utilisateur clique sur OK
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
// quand on appui sur rechercher soit on lui demande de entrer un nom et un numero s'il n'a rien entré,soit on affiche les resultats de la recherche
    private void searchItems() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom ou un numéro de série.");
            return;
        }

        // Avant d'afficher des resultats il faudrait d'abord supprimer les resultats précedents
        listModel.clear();

        // on appelle la fonction qui permet d'effectuer la recherche dans la bd
        List<String> results = searchDatabase(query);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun objet trouvé.");
        } else {
            // pour afficher la liste des resultats les résultats dans la liste
            for (String result : results) {
                listModel.addElement(result);
            }
        }
    }

    private List<String> searchDatabase(String query) {
        List<String> results = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                // requête sql pour la recherche , on ne fait pas attention ici à la casse
                String sql = "SELECT si.name, si.serial_number, si.description, r.name AS reporter_name, si.id_reporter " +
                        "FROM signaled_items si " +
                        "JOIN reporters r ON si.id_reporter = r.id " +
                        "WHERE si.name LIKE ? OR si.serial_number LIKE ?";


                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + query + "%");
                stmt.setString(2, "%" + query + "%");

                ResultSet rs = stmt.executeQuery();
                // on lit chaque ligne de la requête sql et on enregistre les resultats dans la liste results qui va être utilisé pour remplir le listModel
                while (rs.next()) {
                    String itemName = rs.getString("name");
                    String itemSerial = rs.getString("serial_number");
                    String description = rs.getString("description");
                    String reporterName = rs.getString("reporter_name");
                    results.add(itemName + " - " + itemSerial + " - " + description + " (Signalé par: " + reporterName + ")");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    // cette fonction permet de gerer les resultat de la recherche

    private void handleResultSelection() {
        String selectedValue = resultsList.getSelectedValue();
        if (selectedValue != null) {
            JOptionPane.showMessageDialog(this, "Vous avez sélectionné: " + selectedValue);
            // Extraire l'ID du signalant (en fonction du format des résultats dans la liste) pour pouvoir le signaler que son objet a été retrouvé
            String selectedItem = selectedValue.split(" \\(Signalé par: ")[1].replace(")", "");
            String[] splitItem = selectedValue.split(" - ");
            String itemName = splitItem[0];
            String serialNumber = splitItem[1];

            // Ouvrir la classe pour envoyer un message au signalant . Ceci est juste à titre simulatif à l'avenir on pourra vraiment envoyer le message
            ReportMessage reportMessageDialog = new ReportMessage(this, selectedItem, itemName, serialNumber);
            reportMessageDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un objet.");
        }
    }
// j'ai crée un main juste pour pouvoir tester la classe séparement
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame parentFrame = new JFrame();
        SearchItem searchDialog = new SearchItem(parentFrame);
        searchDialog.setVisible(true);
    }
}
