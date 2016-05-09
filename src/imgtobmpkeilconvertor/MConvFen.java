package imgtobmpkeilconvertor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Cette classe jour le rôle de modèle pour la fenetre de conversion.
 * Elle décompose en outre l'image pixel par pixel et réaliser le code de cette dernière
 * @author Mathieu GUYOT (ouranos588)
 * @version 1
 */
public class MConvFen extends Observable{
    private List<Couleur> couleurs;
    private String ImageName;
    private BufferedImage imageBuf;
    private int width;
    private int height;
    
    /**
     * Permet de fixer l'image courante utilisée pour la conversion
     * @param image l'image utilisée pour la conversion
     */
    public void setImage(File image) {
        try {  
            imageBuf = ImageIO.read(image);
            ImageName = image.getName();
            this.width = imageBuf.getWidth();
            this.height = imageBuf.getHeight();
            this.SimpImageName();
        } catch (IOException ex) {
            System.out.println("Impossible de convertir l'image");
            Logger.getLogger(VImageShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Action de lancement de conversion
     */
    public void lancerConversion() {
        couleurs = new ArrayList<>();
        super.setChanged();
        super.notifyObservers();
    }
    
    /**
     * Permet de récupérer toutes les couleurs différentes de l'image et de leur associer leur code 16 bits.
     */
    private void trouverCouleursImage() {
         //Récupération des pixels de l'image
        int i;
        for (int y = imageBuf.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < imageBuf.getWidth(); x++) {
                Color mycolor = new Color(imageBuf.getRGB(x, y));
                boolean couleurExiste = false;
                //On regarde si la couleur est déjà dans la liste des couleurs
                for(Couleur c : this.couleurs) {
                    if(!couleurExiste &&
                       c.getRed() == mycolor.getRed() && 
                       c.getBlue() == mycolor.getBlue() &&
                       c.getGreen() == mycolor.getGreen())
                            couleurExiste = true;
                }
                //si non, on ajoute la nouvelle couleur
                if(!couleurExiste) {
                    couleurs.add(new Couleur(mycolor.getRed(), mycolor.getGreen(), mycolor.getBlue()));
                }
            }
        }
    }
    
//    /**
//     * Permet de retourner les couleurs utilisées par l'image
//     * @return les couleurs utilisées par l'image
//     */
//    public String getCouleurs() {
//        this.trouverCouleursImage();
//        String couleurs = "";
//        
//        for(Couleur c : this.couleurs) {
//            couleurs += "#define C" + c.getHexCode() + "\t 0x" + c.getHexCode() + " ";
//            couleurs += "//(" + c.getRed() + ";" + c.getGreen() + ";" + c.getBlue() + ")";
//            couleurs += "\n";        
//        }
//        
//        return couleurs;
//    }
    
    /**
     * Permet de récupérer le code de l'image (partie .c)
     */
    public String genererCodeCImage() {
        String code = "";
        code += "const unsigned short bmp" + this.ImageName + "[" + this.width + "*"
                + this.height + "]=\n{\n";
        int i;
        for (int y = imageBuf.getHeight() - 1; y >= 0; y--) {
            code +="\t";
            for (int x = 0; x < imageBuf.getWidth(); x++) {
                Color c = new Color(imageBuf.getRGB(x, y));
                // à faire méthode static
                Couleur couleur=new Couleur(c.getRed(), c.getGreen(), c.getBlue());
                code += couleur.getHexCode();
                    
               //     "0x" + Integer.toHexString(trouverCode(c.getRed(), c.getGreen(), c.getBlue()));
                if(y != 0 || x != imageBuf.getWidth()-1) {
                    code += ",";
                }
            }
            code += "\n";
        }
        code += "};";
        return code;
    }
    
    /**
     * Cette fonction permet de simplifier le nom de l'image
     */
    private void SimpImageName() {
        String temp = "";
        for(int i = 0 ; i < this.ImageName.length() ; i++) {
            char car = this.ImageName.charAt(i);
            if((car >= 'a' && car <= 'z') || (car >= 'A' && car <= 'Z') || (car >= '0' && car <= '9')) {
                temp += car;
            }
        }
        this.ImageName = temp;
    }
    
    /**
     * Permet de récupérer le code de l'image (partie .h)
     */
    public String genererCodeHImage() {
        String code = "";
        code += "extern const unsigned short bmp" + this.ImageName + "[" + this.width + "*"
                + this.height + "];";
        return code;
    }
    
    /**
     * Permet de trouver le code décimal de la couleur
     * @param red
     * @param green
     * @param blue
     * @return le code décimal de la couleur
     */
    private int trouverCode(int red, int green, int blue) {
        red >>= 3;
        green >>=2;
        blue >>=3;
        return (red << 11) | (green << 5) | blue;
    }
    
    /**
     * Permet de vérifier si la fonction est apte à être convertie
     * l'afficheur LCD de la carte MCBSTM32EXL étant de 320x240 en mode paysage
     * @return 
     */
    public boolean imageIsValid() {
        return this.width <= 320 && this.height <= 240;
    }
}
