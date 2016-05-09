package imgtobmpkeilconvertor;

/**
 * Cette classe permet de représenter une couleur sous les formes (255,255,255)
 * et d'un nombre décimal allant de 0 à 65535 (soit une couleur codée sur 16
 * bits !)
 *
 * @author mathieu guyot (ouranos588)
 * @version 1
 */
public class Couleur {

    private int red;
    private int green;
    private int blue;
    private int decCode;
    private String hexCode;

    /**
     * Constructeur de la classe Couleur
     *
     * @param red (0-255) red value
     * @param green (0-255) green value
     * @param blue (0-255) blue value
     */
    public Couleur(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        trouverCode();
    }

    /**
     * Cette fonction permet de trouver le nombre décimal de la couleur (de 0 à
     * 65535) à partir de son code RGB. le code décimal est ensuite convertit en
     * hexadécimal
     */
    private void trouverCode() {
        int tempRed = red >> 3;  // recuperation des 5 bits de poids forts
        int tempGreen = green >> 2;// recuperation des 6 bits de poids forts
        int tempBleu = blue >> 3;// recuperation des 5 bits de poids forts
        decCode = (tempRed << 11) | (tempGreen << 5) | tempBleu;
        hexCode = Integer.toHexString(decCode).toUpperCase();

        while (hexCode.length() < 4) {
            hexCode = "0" + hexCode;
        }

        hexCode = "0x" + hexCode;
    }

    /**
     * Getteur sur red (RGB)
     *
     * @return red (RGB)
     */
    public int getRed() {
        return red;
    }

    /**
     * Getteur sur green (RGB)
     *
     * @return green (RGB)
     */
    public int getGreen() {
        return green;
    }

    /**
     * Getteur sur blue (RGB)
     *
     * @return blue (RGB)
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Getteur sur le code décimal de la couleur (0-65535)
     *
     * @return code décimal de la couleur (0-65535)
     */
    public int getDecCode() {
        return decCode;
    }

    /**
     * Getteur sur le code hexadécimal de la couleur (0-65535)
     *
     * @return code hexadécimal de la couleur (0-65535)
     */
    public String getHexCode() {
        return hexCode;
    }
}
