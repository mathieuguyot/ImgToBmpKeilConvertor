package imgtobmpkeilconvertor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Cette classe permet de créer une fenetre fournissant à l'utilisateur de quoi
 * obtenir le code décimal et héxadécimal d'une couleur donnée (255,255,255).
 *
 * @author Mathieu GUYOT (ouranos588)
 * @author RN
 * @version 2
 */
public class VueRGBconv extends JFrame {

    private JPanel pSpinners;
    private JPanel pResults;
    private JSpinner spnRed;
    private JSpinner spnGreen;
    private JSpinner spnBlue;
    private JTextField colorCodeHex;
    private JTextField colorCodeDec;
    private JButton butConv;

    /**
     * Constructeur de la classe VueRGBconv
     */
    public VueRGBconv() {
        pSpinners = new JPanel(new GridLayout(2, 3, 5, 0));
        pResults = new JPanel(new GridLayout(2, 2, 5, 0));

        SpinnerNumberModel modelRed = new SpinnerNumberModel(0, 0, 255, 1);
        SpinnerNumberModel modelGreen = new SpinnerNumberModel(0, 0, 255, 1);
        SpinnerNumberModel modelBleu = new SpinnerNumberModel(0, 0, 255, 1);

        spnRed = new JSpinner(modelRed);
        spnGreen = new JSpinner(modelGreen);
        spnBlue = new JSpinner(modelBleu);

        colorCodeDec = new JTextField(10);
        colorCodeHex = new JTextField(10);

        butConv = new JButton("Convertir");
        butConv.addActionListener(new ConversionListener());
       
        this.ajouterEtPlacerComp();

        super.setTitle("Conversion couleur");
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.pack();
        super.setResizable(false);
        super.setVisible(true);
    }

    /**
     * Cette fonction permet de placer les différents composants dans la fenetre
     */
    private void ajouterEtPlacerComp() {
        // initialisation des labels
        JLabel lbRed = new JLabel("rouge :");
        JLabel lbGreen = new JLabel("vert :");
        JLabel lbBlue = new JLabel("bleu :");
        JLabel lbCodeDec = new JLabel("Décimal :", JLabel.RIGHT);
        JLabel lbCodeHex = new JLabel("Hexadécimal :", JLabel.RIGHT);

        //Ajout des composants dans les panels
        pSpinners.add(lbRed);
        pSpinners.add(lbGreen);
        pSpinners.add(lbBlue);
        pSpinners.add(spnRed);
        pSpinners.add(spnGreen);
        pSpinners.add(spnBlue);

        pResults.add(lbCodeDec);
        pResults.add(colorCodeDec);
        pResults.add(lbCodeHex);
        pResults.add(colorCodeHex);

        //Ajout des panels et du bouton dans la frame
        super.setLayout(new BorderLayout());
        super.add(pSpinners, BorderLayout.NORTH);
        super.add(pResults, BorderLayout.CENTER);
        super.add(butConv, BorderLayout.SOUTH);
    }

    public class ConversionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int code = this.trouverCode((int) VueRGBconv.this.spnRed.getValue(),
                (int) VueRGBconv.this.spnGreen.getValue(),
                (int) VueRGBconv.this.spnBlue.getValue());
            VueRGBconv.this.colorCodeDec.setText(String.valueOf(code));
            VueRGBconv.this.colorCodeHex.setText("0x" + Integer.toHexString(code).toUpperCase());
        }

        /**
         * Permet de trouver le code sur 16bits de la couleur
         *
         * @param red entier non signe entre 0 et 255
         * @param green entier non signe entre 0 et 255
         * @param blue entier non signe entre 0 et 255
         * @return le code 16bits de la couleur
         */
        private int trouverCode(int red, int green, int blue) {
            red >>= 3; // garde les 5 bits de poids forts
            green >>= 2; // garde les 6 bits de poids forts
            blue >>= 3; // garde les 5 bits de poids forts
            return (red << 11) | (green << 5) | blue;
        }
    }
}
