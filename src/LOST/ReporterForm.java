package LOST;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
public class ReporterForm extends JDialog {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    private String itemName, itemSerial, itemDescription;

    // reportform qui a pour parent reportitems
    public ReporterForm(JDialog parent, String name, String serial, String description) {
        super(parent, "Vos informations", true);
        this.itemName = name;
        this.itemSerial = serial;
        this.itemDescription = description;

        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nom:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Téléphone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        JButton submitButton = new JButton("Soumettre");
        submitButton.addActionListener(e -> handleSubmit());
        panel.add(new JLabel());
        panel.add(submitButton);

        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void handleSubmit() {
        String reporterName = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (reporterName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nom et email sont obligatoires.");
            return;
        }
        // on enregistre les valeurs que l'utilisateur a entré
        int reporterId = Insertion.insertReporter(reporterName, email, phone);
        if (reporterId == -1) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement.");
            return;
        }
        // on recupère la date actuelle et on cree un nouveau lost items avec pour reported date la date qu'on a recupéré
        Date now = new Date(System.currentTimeMillis());
        Lost_items item = new Lost_items(itemName, itemSerial, itemDescription, now, reporterId);
        Insertion.insertSignaledItem(item,reporterId); // on appelle une methode de la classe insertion pour inserer le nouveau élement dans la bd

        JOptionPane.showMessageDialog(this, "Objet signalé avec succès !");
        dispose();
    }
}
