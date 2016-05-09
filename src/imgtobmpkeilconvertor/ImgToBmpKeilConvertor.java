package imgtobmpkeilconvertor;

/*
 * Ce logiciel permet de convertir n'importe quelle image en code c afin d'être
 * par la suite porté sur microcotroleur (via keil uvision par exemple)
 * Le programme s'occupe de :
 * > Trouver les couleurs de l'image et les convertir sur un code de code de 16 bits (0-65535)
 * > Décomposer l'image pixel par pixel et la recomposer sous un code adapté au portage sur microcontroleur
    (chaque pixel de l'image est associé au code couleur de l'image de 16 bits)
 * > Fournir le code obtenu prêt à l'emploi à l'utilisateur
 * > Fournir des conseils à l'utilisateur sur le choix des images (taille, nombre de couleurs, ...) 
     pour que le portage sur le microcontroleur reste possible.
 */

/**
 * Cette classe contient la fonction main du programme
 * @author mathieu guyot (ouranos588)
 */
public class ImgToBmpKeilConvertor {

    /**
     * Fonction main, le point d'entrée du programme
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MConvFen modele = new MConvFen();
        VConvFen vue = new VConvFen(modele);
    }
}
