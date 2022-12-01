/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.Files;
import tools.Constantes;
import tools.Point;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import component.element.Accessoire;
import component.element.Cote;
import component.Element;
import component.element.accessoire.Fenetre;
import component.element.Mur;
import component.element.accessoire.Porte;
import component.element.accessoire.Prise;
import component.element.accessoire.RetourAir;
import component.Salle;
import component.element.Separator;
import component.element.accessoire.TrouAir;
import tools.Config;
import tools.Utils;

/**
 * @author dowes
 */
public class Controller {

    private Salle salle;
    private Cote cote;
    private boolean displayGrille = false;
    private String fileSave = null;

    public Controller() {
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Cote getCote() {
        return cote;
    }

    public void setCote(Cote cote) {
        this.cote = cote;
    }

    public boolean isDisplayGrille() {
        return displayGrille;
    }

    public void setDisplayGrille(boolean displayGrille) {
        this.displayGrille = displayGrille;
    }

    public Point getPoint(Point point, double level) {
        return new Point((int) (point.getX() * level), (int) (point.getY() * level));
    }

    public void newProjet() {
        salle = new Salle();
        try {
            salle.setOrigin(new Point(Config.locationX, Config.locationY));
            salle.setWidth(Config.salleWidth);
            salle.setLength(Config.salleLength);
            salle.setSeparator(Config.separatorWidth);
            salle.setWeight(Config.murWeight);
            salle.setHeight(Config.murHeight);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        salle.build();
    }

    public boolean addSeparator(Mur mur, Cote cote, final Point point) {
        try {
            if (mur != null) {
                int index = cote.getElements().indexOf(mur);
                Mur murGauche = (Mur) mur;
                int y = murGauche.getA().y;
                int x = (point.x + cote.getSalle().getSeparator());
                int width = (murGauche.getB().x - x);
                murGauche.setWidth(point.x - murGauche.getA().x);

                Mur murDroite = new Mur(cote.getElements().size() + 1, cote, width);
                murDroite.setA(new Point(x, y));

                Separator item = new Separator(cote.getElements().size() + 1, cote, murGauche, murDroite);
                item.setA(new Point(point.x, y));

                cote.getElements().add(index + 1, item.build());
                cote.getElements().add(index + 2, murDroite.build());

                if (index > -1) {
                    cote.getElements().set(index, (Element) murGauche.build());
                }
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addPorte(Mur mur, final Point point) {
        try {
            Porte item = new Porte(mur.getAccessoires().size() + 1, mur, Config.porteWidth, Config.porteHeight);
            Utils.controlPoint(mur, point, item.getWidth(), item.getHeight());
            item.setA(point);
            mur.getAccessoires().add(item.build());
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addFenetre(Mur mur, final Point point) {
        try {
            Fenetre item = new Fenetre(mur.getAccessoires().size() + 1, mur, Config.fenetreWidth, Config.fenetreHeight);
            Utils.controlPoint(mur, point, item.getWidth(), item.getHeight());
            item.setA(point);
            mur.getAccessoires().add(item.build());
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addPrise(Mur mur, final Point point) {
        try {
            Prise item = new Prise(mur.getAccessoires().size() + 1, mur, Config.priseWidth, Config.priseHeight);
            Utils.controlPoint(mur, point, item.getWidth(), item.getHeight());
            item.setA(point);
            mur.getAccessoires().add(item.build());
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addRetour(Mur mur, final Point point) {
        try {
            RetourAir item = new RetourAir(mur.getAccessoires().size() + 1, mur, Config.retourWidth, Config.retourHeight);
            Utils.controlPoint(mur, point, item.getWidth(), item.getHeight());
            item.setA(point);
            mur.getAccessoires().add(item.build());

            TrouAir trou = new TrouAir(mur.getAccessoires().size() + 1, mur, item, Config.retourWidth, Config.retourHeight);
            Utils.controlPoint(mur, point, trou.getWidth(), trou.getHeight());
            trou.setA(point);
            mur.getAccessoires().add(trou.build());
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean removeAccessoire(Accessoire element) {
        try {
            Mur mur = element.getMur();
            mur.getAccessoires().remove(element);
            if (element instanceof RetourAir) {
                for (Accessoire item : mur.getAccessoiresExterne()) {
                    if (item instanceof TrouAir ? ((TrouAir) item).getRetourAir().equals(element) : false) {
                        mur.getAccessoires().remove(item);
                    }
                }
            } else if (element instanceof TrouAir) {
                mur.getAccessoires().remove(((TrouAir) element).getRetourAir());
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean removeSeparator(Separator element) {
        try {
            Cote cote = element.getCote();
            Mur murDroite = element.getMurDroite();
            int index = cote.getElements().indexOf(murDroite);
            Mur murGauche = element.getMurGauche();
            murDroite.setWidth(murDroite.getWidth() + cote.getSalle().getSeparator() + murGauche.getWidth());
            if (index > -1) {
                cote.getElements().set(index, (Element) murDroite.build());
            }
            cote.getElements().remove(element);
            cote.getElements().remove(murGauche);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void drawGrille(Graphics g, int width, int heigth) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            g.setColor(Color.LIGHT_GRAY);
            int[] pointX;
            int[] pointY;
            int indexX;
            int indexY = 0;
            while (indexY < heigth) {
                indexX = 0;
                while (indexX < width) {
                    pointX = new int[]{indexX, indexX + Config.grilleLength, indexX + Config.grilleLength, indexX};
                    pointY = new int[]{indexY, indexY, indexY + Config.grilleLength, indexY + Config.grilleLength};
                    g.drawPolygon(pointX, pointY, 4);
                    indexX += Config.grilleLength;
                }
                indexY += Config.grilleLength;
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Salle onRestoreBackup(JFrame frame) {
        Salle result = null;
        try {
            FileFilter filterJson = new FileNameExtensionFilter("JSON", "json");
            FileFilter filterXml = new FileNameExtensionFilter("XML", "xml");
            JFileChooser fileChooser = new JFileChooser("");
            fileChooser.addChoosableFileFilter(filterJson);
            fileChooser.addChoosableFileFilter(filterXml);
            fileChooser.showOpenDialog(frame);
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                return null;
            }
            String extention = Files.getFileExtension(file.getAbsolutePath());
            if (extention.equals("xml")) {
                result = onRestoreXML(file);
            } else {
                result = onRestoreJSON(file);
            }
            fileSave = file.getAbsolutePath();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private Salle onRestoreXML(File file) {
        try {
//            JAXBContext context = JAXBContext.newInstance(Salle.class);
//            Marshaller m = context.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
//            m.marshal(item, file);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Salle onRestoreJSON(File file) {
        Salle result = null;
        try {
            result = Constantes.GSON.fromJson(new InputStreamReader(new FileInputStream(file)), Salle.class);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean onGenerateBackup(JFrame frame, Salle item, boolean create) {
        try {
            if (Utils.asString(fileSave) && create) {
                fileSave = null;
            }
            File file;
            if (!Utils.asString(fileSave)) {
                FileFilter filterJson = new FileNameExtensionFilter("JSON", "json");
                FileFilter filterXml = new FileNameExtensionFilter("XML", "xml");
                JFileChooser fileChooser = new JFileChooser("");
                fileChooser.setFileFilter(filterJson);
                fileChooser.addChoosableFileFilter(filterJson);
                fileChooser.addChoosableFileFilter(filterXml);
                fileChooser.showSaveDialog(frame);
                file = fileChooser.getSelectedFile();
                if (file == null) {
                    return false;
                }
                FileFilter filter = fileChooser.getFileFilter();
                String extention = Files.getFileExtension(file.getAbsolutePath());
                if (!Utils.asString(extention)) {
                    file = new File(file.getAbsolutePath() + "." + filter.getDescription().toLowerCase());
                }
            } else {
                file = new File(fileSave);
            }
            if (file.exists()) {
                file.delete();
            }
            String extention = Files.getFileExtension(file.getAbsolutePath());
            if (extention.equals("xml")) {
                file = onGenerateXML(file, item);
            } else {
                file = onGenerateJSON(file, item);
            }
            if (fileSave == null) {
                fileSave = file.getAbsolutePath();
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    private File onGenerateXML(File file, Salle item) {
        try {
            JAXBContext context = JAXBContext.newInstance(Salle.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
            m.marshal(item, file);
        } catch (JAXBException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    private File onGenerateJSON(File file, Salle item) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, item);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

}
