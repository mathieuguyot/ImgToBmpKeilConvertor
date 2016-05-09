package imgtobmpkeilconvertor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * Cette classe permet la visualisation d'une image dans une fenetre.
 * @author Mathieu GUYOT (ouranos588)
 * @version 1
 */
public class VImageShow extends JFrame{
    private File image;
    private BufferedImage imageBuf;

    /**
     * Constructeur de VImageShow
     * @param image l'image à visualiser dans la fenêtre.
     */
    public VImageShow(File image) {
        this.image = image;
        try {  
            imageBuf = ImageIO.read(this.image);
        } catch (IOException ex) {
            System.out.println("L'image ne peut être lue");
            Logger.getLogger(VImageShow.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.setContentPane(new JScrollPane(new JLabel(new ImageIcon(imageBuf))));
        super.pack();
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setTitle(image.getPath());
        super.setVisible(true);
    }
}
