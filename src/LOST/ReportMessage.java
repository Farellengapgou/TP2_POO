package LOST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ReportMessage extends JDialog {

    private JTextArea messageField;
    private JButton sendButton;
    private String reporterName;
    private String itemName;
    private String serialNumber;

    public ReportMessage(JDialog parent, String reporterName, String itemName, String serialNumber) {
        super(parent, "Envoyer un message au signalant", true);
        // le parent est la fenêtre precedeente celle de la recherche
        setSize(400, 300);
        setLocationRelativeTo(parent);

        this.reporterName = reporterName;
        this.itemName = itemName;
        this.serialNumber = serialNumber;


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // on cree ici une zone de texte pour qu'il puisse décrire comment il est entré en contact avec l'objet
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Décrivez où et quand vous avez vu cet objet:");
        messagePanel.add(label);

        messageField = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(messageField);
        messagePanel.add(scrollPane);

        panel.add(messagePanel, BorderLayout.CENTER);


        sendButton = new JButton("Envoyer le message");
        sendButton.addActionListener(e -> sendMessageToReporter());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);

        // on ferme  la fenêtre si l'utilisateur clique sur quitter
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void sendMessageToReporter() {
        String message = messageField.getText();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un message.");
            return;
        }

        // Simuler l'envoi du message (pour le moment afficher un message)
        JOptionPane.showMessageDialog(this, "Message envoyé à " + reporterName + ".");
        dispose(); // Fermer la fenêtre après l'envoi
    }
}
