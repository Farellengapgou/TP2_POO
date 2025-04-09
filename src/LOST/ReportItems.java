package LOST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ReportItems extends JDialog {

    private JTextField nameField;
    private JTextField serialField;
    private JTextArea descriptionField;

    public ReportItems(JFrame parent) {
        super(parent, "Signaler un objet", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nom de l'objet:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Numéro de série:"));
        serialField = new JTextField();
        panel.add(serialField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextArea(3, 20);
        panel.add(new JScrollPane(descriptionField));

        JButton nextButton = new JButton("Suivant");
        nextButton.addActionListener(e -> openReporterForm());
        panel.add(new JLabel()); // Espace vide
        panel.add(nextButton);

        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
// on cree un formulaire pour recuperer les informations sur l'objet à signaler
    private void openReporterForm() {
        String name = nameField.getText();
        String serial = serialField.getText();
        String description = descriptionField.getText();

        if (name.isEmpty() || serial.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        // reportform a pour parent reportItems
        new ReporterForm(this, name, serial, description).setVisible(true);
        dispose(); // Fermer la fenêtre actuelle
    }

}
