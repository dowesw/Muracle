/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author LYMYTZ
 */
public class Config {

    public static int locationX;

    public static int locationY;

    public static int salleWidth;

    public static int salleLength;

    public static int murWidth;

    public static int murHeight;

    public static int murWeight;

    public static int separatorWidth;

    public static int porteWidth;

    public static int porteHeight;

    public static int fenetreWidth;

    public static int fenetreHeight;

    public static int priseWidth;

    public static int priseHeight;

    public static int retourWidth;

    public static int retourHeight;

    public static int retourWeight;

    public static int retourDistanceSol;

    public static int trouWidth;

    public static int trouHeight;

    public static int grilleLength;

    public static int pliHeight;

    public static int angleCoinPli;

    public static int decaleEpaisseurMur;

    public static void load() {
        try {
            try (InputStream input = Constantes.LOADER.getResourceAsStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(input);

                String _locationX_ = prop.getProperty("location.x");
                locationX = Utils.asString(_locationX_) ? Integer.valueOf(_locationX_) : 0;

                String _locationY_ = prop.getProperty("location.y");
                locationY = Utils.asString(_locationY_) ? Integer.valueOf(_locationY_) : 0;

                String _salleWidth_ = prop.getProperty("salle.width");
                salleWidth = Utils.asString(_salleWidth_) ? Integer.valueOf(_salleWidth_) : 0;

                String _salleLength_ = prop.getProperty("salle.length");
                salleLength = Utils.asString(_salleLength_) ? Integer.valueOf(_salleLength_) : 0;

                String _murWidth_ = prop.getProperty("mur.width");
                murWidth = Utils.asString(_murWidth_) ? Integer.valueOf(_murWidth_) : 0;

                String _murHeight_ = prop.getProperty("mur.height");
                murHeight = Utils.asString(_murHeight_) ? Integer.valueOf(_murHeight_) : 0;

                String _murWeight_ = prop.getProperty("mur.weight");
                murWeight = Utils.asString(_murWeight_) ? Integer.valueOf(_murWeight_) : 0;

                String _separatorWidth_ = prop.getProperty("separator.width");
                separatorWidth = Utils.asString(_separatorWidth_) ? Integer.valueOf(_separatorWidth_) : 0;

                String _porteWidth_ = prop.getProperty("porte.width");
                porteWidth = Utils.asString(_porteWidth_) ? Integer.valueOf(_porteWidth_) : 0;

                String _porteHeigth_ = prop.getProperty("porte.height");
                porteHeight = Utils.asString(_porteHeigth_) ? Integer.valueOf(_porteHeigth_) : 0;

                String _fenetreWidth_ = prop.getProperty("fenetre.width");
                fenetreWidth = Utils.asString(_fenetreWidth_) ? Integer.valueOf(_fenetreWidth_) : 0;

                String _fenetreHeigth_ = prop.getProperty("fenetre.height");
                fenetreHeight = Utils.asString(_fenetreHeigth_) ? Integer.valueOf(_fenetreHeigth_) : 0;

                String _priseWidth_ = prop.getProperty("prise.width");
                priseWidth = Utils.asString(_priseWidth_) ? Integer.valueOf(_priseWidth_) : 0;

                String _priseHeigth_ = prop.getProperty("prise.height");
                priseHeight = Utils.asString(_priseHeigth_) ? Integer.valueOf(_priseHeigth_) : 0;

                String _retourWidth_ = prop.getProperty("retour.width");
                retourWidth = Utils.asString(_retourWidth_) ? Integer.valueOf(_retourWidth_) : 0;

                String _retourHeigth_ = prop.getProperty("retour.height");
                retourHeight = Utils.asString(_retourHeigth_) ? Integer.valueOf(_retourHeigth_) : 0;

                String _retourWeight_ = prop.getProperty("retour.weight");
                retourWeight = Utils.asString(_retourWeight_) ? Integer.valueOf(_retourWeight_) : 0;

                String _retourDistanceSol_ = prop.getProperty("retour.distance.sol");
                retourDistanceSol = Utils.asString(_retourDistanceSol_) ? Integer.valueOf(_retourDistanceSol_) : 0;

                String _trouWidth_ = prop.getProperty("trou.width");
                trouWidth = Utils.asString(_trouWidth_) ? Integer.valueOf(_trouWidth_) : 0;

                String _trouHeigth_ = prop.getProperty("trou.height");
                trouHeight = Utils.asString(_trouHeigth_) ? Integer.valueOf(_trouHeigth_) : 0;

                String _grilleLength_ = prop.getProperty("grille.length");
                grilleLength = Utils.asString(_grilleLength_) ? Integer.valueOf(_grilleLength_) : 0;

                String _pliHeight_ = prop.getProperty("pli.height");
                pliHeight = Utils.asString(_pliHeight_) ? Integer.valueOf(_pliHeight_) : 0;

                String _angleCoinPli_ = prop.getProperty("angle.coin.pli");
                angleCoinPli = Utils.asString(_angleCoinPli_) ? Integer.valueOf(_angleCoinPli_) : 0;

                String _decaleEpaisseurMur_ = prop.getProperty("decale.epaisseur.mur");
                decaleEpaisseurMur = Utils.asString(_decaleEpaisseurMur_) ? Integer.valueOf(_decaleEpaisseurMur_) : 0;
            } catch (Exception ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
