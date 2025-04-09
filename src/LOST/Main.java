package LOST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;



public class Main {

    public static void main(String[] args) {
        // pour faire que l'apparence de mon appli ressemble à celui de ma machine
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("ACCUEIL");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panneau de fond avec dégradé
        JPanel panel_fond = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(40, 40, 45),
                        0, getHeight(), new Color(70, 30, 35)
                );
                g2d.setPaint(gradient);
                g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
            }
        };

        panel_fond.setLayout(new BorderLayout());
        frame.setContentPane(panel_fond);

        placeComponents(panel_fond);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Réagir au redimensionnement si nécessaire
            }
        });

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        // Créer un panneau pour le titre avec effet 3D
        JPanel titrePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
            }
        };
        titrePanel.setPreferredSize(new Dimension(panel.getWidth(), 200));

        JPanel nom_appli3D = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int width = getWidth();
                int fontSize = width / 12;
                fontSize = Math.max(40, Math.min(fontSize, 120)); // Limites pour la taille de la police

                Font titleFont = new Font("SansSerif", Font.BOLD, fontSize);
                g2d.setFont(titleFont);

                String fullText = "LOST_N_FOUND";

                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(fullText);
                int x = (width - textWidth) / 2;
                int y = getHeight() / 2 + fm.getHeight() / 3;

                for (int i = 7; i > 0; i--) {
                    g2d.setColor(new Color(0, 0, 0, 30 + 30 / i));
                    g2d.drawString(fullText, x + i, y + i);
                }

                GradientPaint titleGradient = new GradientPaint(
                        x, y - fm.getHeight(), Color.BLACK,
                        x, y + fm.getDescent(), new Color(180, 20, 20)
                );
                g2d.setPaint(titleGradient);
                g2d.drawString(fullText, x, y);

                g2d.setColor(new Color(255, 255, 255, 40));
                g2d.drawString(fullText, x - 1, y - 1);
            }
        };

        nom_appli3D.setPreferredSize(new Dimension(panel.getWidth(), 150));
        titrePanel.add(nom_appli3D, BorderLayout.CENTER);
        panel.add(titrePanel, BorderLayout.NORTH);

        // Panneau central
        JPanel centrePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(60, 60, 65, 200));
                g2d.fillRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 25, 25);
                g2d.setColor(new Color(100, 30, 30, 150));
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 25, 25);

                // Dessin direct du texte sur le panneau central
                String message = "Vérifiez si les objets que vous souhaitez acheter sont volés ou pas et signalez des objets volés";

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Taille de police adaptative
                int fontSize = Math.max(16, Math.min(getWidth() / 40, 22));
                Font descFont = new Font("SansSerif", Font.BOLD, fontSize);
                g2d.setFont(descFont);

                FontMetrics fm = g2d.getFontMetrics();
                int lineHeight = fm.getHeight();
                int availableWidth = getWidth() - 120; // Marges

                // Préparation du texte multi-lignes
                java.util.List<String> lines = new java.util.ArrayList<>();
                String[] words = message.split(" ");
                StringBuilder currentLine = new StringBuilder();

                for (String word : words) {
                    String testLine = currentLine.length() > 0 ?
                            currentLine + " " + word : word;
                    if (fm.stringWidth(testLine) <= availableWidth) {
                        if (currentLine.length() > 0) currentLine.append(" ");
                        currentLine.append(word);
                    } else {
                        lines.add(currentLine.toString());
                        currentLine = new StringBuilder(word);
                    }
                }

                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                }


                int yPosition = 60;


                for (String line : lines) {
                    int textWidth = fm.stringWidth(line);
                    int x = (getWidth() - textWidth) / 2;

                    //Ombre décalé pour avoir  l'effet 3D
                    for (int i = 3; i > 0; i--) {
                        g2d.setColor(new Color(0, 0, 0, 40 / i));
                        g2d.drawString(line, x + i, yPosition + i);
                    }


                    GradientPaint textGradient = new GradientPaint(
                            x, yPosition - fm.getHeight(), new Color(220, 220, 220),
                            x, yPosition + fm.getDescent(), new Color(180, 100, 100)
                    );
                    g2d.setPaint(textGradient);
                    g2d.drawString(line, x, yPosition);

                    g2d.setColor(new Color(255, 255, 255, 40));
                    g2d.drawString(line, x - 1, yPosition - 1);

                    yPosition += lineHeight;
                }
            }
        };

        centrePanel.setOpaque(false);
        centrePanel.setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 40, 15, 40);


        Component rigidArea = Box.createRigidArea(new Dimension(0, 100));
        centrePanel.add(rigidArea, gbc);

        // creation des bouttons de signalement et de recherche
        JButton chercherButton = createStyledButton("Vérifier un objet");
        JButton signalerButton = createStyledButton("Signaler un vol");

        chercherButton.addActionListener(e -> {
            SearchItem searchDialog = new SearchItem((JFrame) SwingUtilities.getWindowAncestor(panel));
            searchDialog.setVisible(true);
        });

        signalerButton.addActionListener(e -> {
            ReportItems ReportDialog = new ReportItems((JFrame) SwingUtilities.getWindowAncestor(panel));
            ReportDialog.setVisible(true);
        });

        centrePanel.add(chercherButton, gbc);
        centrePanel.add(signalerButton, gbc);
        panel.add(centrePanel, BorderLayout.CENTER);

        centrePanel.revalidate(); // recalcule les  positions
        centrePanel.repaint();    // redessine l'interface lorsqu'on essaye d'ajuster la taille
    }

    // ici on definit une méthode pour creer des bouttons plutôt cool
    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Dégradé du bouton
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(150, 40, 40),
                        0, getHeight(), new Color(100, 20, 20)
                );
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Bordure du bouton
                g2d.setColor(new Color(180, 60, 60));
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 18, 18);

                // Effet de lumière sur le haut du bouton
                GradientPaint highlight = new GradientPaint(
                        0, 0, new Color(255, 255, 255, 100),
                        0, getHeight()/2, new Color(255, 255, 255, 0)
                );
                g2d.setPaint(highlight);
                g2d.fillRoundRect(3, 3, getWidth()-6, getHeight()/2-3, 16, 16);

                // Texte avec ombre légère
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getHeight();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + fm.getAscent();

                // Ombre du texte
                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.drawString(getText(), x+1, y+1);

                // Texte principal
                g2d.setColor(new Color(255, 255, 255));
                g2d.drawString(getText(), x, y);
            }
        };

        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(120, 30, 30));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(200, 50));

        // Effet de survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return button;
    }
}