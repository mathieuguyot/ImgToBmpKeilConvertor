package imgtobmpkeilconvertor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

/**
 * Cette classe permet de créer une fenetre qui permet à l'utilisateur de
 * réaliser les actions : charger une image, voir l'image, lancer la conversion
 * et récupèrer le résultat de la conversion
 *
 * @author Mathieu GUYOT (ouranos588)
 * @author  RN
 * @version 2
 */
public class VConvFen extends JFrame implements Observer {

    private MConvFen modele;
    private JButton butLoadImg;
    private JButton butLancerConv;
    private JButton butVoirImage;
    private JButton butOpenfenColorConv;
    private JButton butAPropos;
    private JTextArea taLog;
    private JTextArea taBMPArrayCRes;
    private JTextArea taBMPArrayHRes;
    private File image;

    /**
     * Constructeur de la classe VConvFen
     *
     * @param modele le modèle associé à la vue
     */
    public VConvFen(MConvFen modele) {
        this.modele = modele;
        modele.addObserver(this);

        butLoadImg = new JButton("Charger une image");
        butLoadImg.addActionListener(new LoadImgListener());
        butLancerConv = new JButton("Lancer conversion");
        butLancerConv.addActionListener(new LancerConvListener());
        butLancerConv.setEnabled(false);
        butVoirImage = new JButton("Voir l'image");
        butVoirImage.addActionListener(new ShowImgListener());
        butVoirImage.setEnabled(false);
        
        taLog = new JTextArea(5, 43);
        DefaultCaret caret = (DefaultCaret) taLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        taLog.setText("En attente du chargement d'une image...");
        taLog.setFocusable(false);
        taBMPArrayCRes = new JTextArea(20, 43);
        taBMPArrayHRes = new JTextArea(2, 43);
        
        butOpenfenColorConv = new JButton("Convertir une couleur");
        butOpenfenColorConv.addActionListener(new ConvCouleurLIstener());
        butAPropos = new JButton("à propos");
        butAPropos.addActionListener(new InfosLIstener());

        this.ajouterEtPlacerComp();

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setMinimumSize(new Dimension(550, 680));
        super.pack();
        super.setResizable(false);
        super.setTitle("Convertisseur Image vers code pour librairie LCD Keil");
        super.setVisible(true);
    }

    /**
     * Cette fonction permet de placer les différents composants dans la fenetre
     */
    private void ajouterEtPlacerComp() {
        JLabel lbLog = new JLabel("Avancée de la conversion");
        JLabel lbTAcodeC = new JLabel("Résultat de la conversion (à placer dans un fichier.c)");
        JLabel lbTAcodeH = new JLabel("Résultat de la conversion (à placer dans un fichier.h)");

        JScrollPane scpanLog = new JScrollPane(this.taLog);
        JScrollPane scpanCodeC = new JScrollPane(this.taBMPArrayCRes);
        JScrollPane scpanCodeH = new JScrollPane(this.taBMPArrayHRes);

        super.add(this.butLoadImg);
        super.add(this.butLancerConv);
        super.add(this.butVoirImage);
        super.add(scpanLog);
        super.add(scpanCodeC);
        super.add(scpanCodeH);
        super.add(lbLog);
        super.add(lbTAcodeC);
        super.add(lbTAcodeH);
        super.add(butOpenfenColorConv);
        super.add(butAPropos);

        //layout
        SpringLayout layout = new SpringLayout();
        super.setLayout(layout);

        //placement des boutons
        // contraintes horizontales
        layout.putConstraint(SpringLayout.WEST, this.butLoadImg, 10,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, this.butLancerConv, 10,
            SpringLayout.EAST, this.butLoadImg);
        layout.putConstraint(SpringLayout.WEST, this.butVoirImage, 10,
            SpringLayout.EAST, this.butLancerConv);
        // contraintes verticales
        layout.putConstraint(SpringLayout.NORTH, this.butLoadImg, 10,
            SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, this.butLancerConv, 10,
            SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, this.butVoirImage, 10,
            SpringLayout.NORTH, this);

        //placement des labels et des textAreas
        //contraintes horizontales sur les labels
        layout.putConstraint(SpringLayout.WEST, lbLog, 10,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, lbTAcodeC, 10,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, lbTAcodeH, 10,
            SpringLayout.WEST, this);
        // contraintes horizontales sur les textAreas
        layout.putConstraint(SpringLayout.WEST, scpanLog, 10,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, scpanCodeC, 10,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, scpanCodeH, 10,
            SpringLayout.WEST, this);

        // contraintes verticales
        layout.putConstraint(SpringLayout.NORTH, lbLog, 10,
            SpringLayout.SOUTH, butLoadImg);
        layout.putConstraint(SpringLayout.NORTH, scpanLog, 5,
            SpringLayout.SOUTH, lbLog);
        layout.putConstraint(SpringLayout.NORTH, lbTAcodeC, 10,
            SpringLayout.SOUTH, scpanLog);
        layout.putConstraint(SpringLayout.NORTH, scpanCodeC, 5,
            SpringLayout.SOUTH, lbTAcodeC);
        layout.putConstraint(SpringLayout.NORTH, lbTAcodeH, 10,
            SpringLayout.SOUTH, scpanCodeC);
        layout.putConstraint(SpringLayout.NORTH, scpanCodeH, 10,
            SpringLayout.SOUTH, lbTAcodeH);

        // placement des boutons de la zone basse
        layout.putConstraint(SpringLayout.NORTH, butOpenfenColorConv, 20,
            SpringLayout.SOUTH, scpanCodeH);
        layout.putConstraint(SpringLayout.WEST, butOpenfenColorConv, 10,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, butAPropos, 0,
            SpringLayout.NORTH, butOpenfenColorConv);
        layout.putConstraint(SpringLayout.WEST, this.butAPropos, 400,
            SpringLayout.WEST, this);
    }

    /**
     * Fonction de mise à jour de la vue
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        String consText = this.taLog.getText();
        this.taLog.setText(consText);
        consText += "\nRécupération du code de l'image...";
        consText += "\nCe traitement peut être long.";
        this.taLog.setText(consText);
        this.taBMPArrayCRes.setText(modele.genererCodeCImage());
        this.taBMPArrayHRes.setText(modele.genererCodeHImage());
        consText += "\nConversion réussie";
        this.taLog.setText(consText);
    }

    /**
     * Classe listener pour le bouton de chargement d'image. Créer un
     * JFileChooser qui permet la récupération d'une image
     */
    public class LoadImgListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            final JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(imageFilter);
            fc.setAcceptAllFileFilterUsed(false);
            int returnVal = fc.showOpenDialog(VConvFen.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                image = fc.getSelectedFile();
                if (image != null) {
                    modele.setImage(image);
                    if (modele.imageIsValid()) {
                        VConvFen.this.butLancerConv.setEnabled(true);
                        VConvFen.this.butVoirImage.setEnabled(true);
                        String consText = VConvFen.this.taLog.getText();
                        consText += "\nL'image " + image.getPath() + " a correctement été chargée.";
                        consText += "\nAttente du lancement de la conversion...";
                        VConvFen.this.taLog.setText(consText);
                    } else {
                        String consText = VConvFen.this.taLog.getText();
                        consText += "\nErreur lors du chargement de l'image, elle dépasse 320*240 pixels";
                        VConvFen.this.taLog.setText(consText);
                    }
                }
            }
        }
    }

    /**
     * Classe listener pour le bouton de visionner une image. Créer un
     * VImageShow qui permet de visionner une image
     */
    public class ShowImgListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            VImageShow show = new VImageShow(image);
            String consText = VConvFen.this.taLog.getText();
            consText += "\nVisualisation de l'image " + image.getPath();
            VConvFen.this.taLog.setText(consText);
        }
    }

    /**
     * Classe listener pour le bouton de lancement de conversion
     */
    public class LancerConvListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            VConvFen.this.taBMPArrayCRes.setText("");
            VConvFen.this.taBMPArrayHRes.setText("");
            VConvFen.this.modele.lancerConversion();
        }
    }

    /**
     * Classe listener pour le bouton de lancement de conversion d'une couleur
     */
    public class ConvCouleurLIstener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            VueRGBconv fen = new VueRGBconv();
        }
    }

    /**
     * Classe listener pour le bouton de lancement de la fen d'infos
     */
    public class InfosLIstener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            VILogicielInfos inf = new VILogicielInfos();
        }
    }
}
