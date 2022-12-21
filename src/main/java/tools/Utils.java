/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import component.Salle;
import component.drawing.DrawingPlan;
import component.drawing.IDrawing;
import component.element.Cote;
import component.element.Mur;
import component.element.Separator;
import component.Element;
import domain.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dowes
 */
public class Utils {

    public static boolean asString(String value) {
        return value != null ? value.trim().length() > 0 : false;
    }

    /**
     * Fontion qui indique si le point du curseur appartient aux points d'un
     * élément
     *
     * @param element
     * @param point
     * @return
     */
    public static boolean checkPointInElementOLD(Element element, Point point) {
        try {
            int[] x = element.getXPoints();
            int[] y = element.getYPoints();
            new Polygon(x, y, x.length).contains(point.getPoint());
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean checkPointInElement(Element element, Point point) {
        try {
            /**
             * Détermination des intervals x
             */
            int x1 = element.getA().x;
            int x2 = element.getB().x;

            /**
             * Détermination des intervals y
             */
            int y1 = element.getA().y;
            int y2 = element.getD().y;
            /**
             * Vérification si le point se trouve dans les intervals
             */
            if ((x1 <= point.x && x2 >= point.x) && (y1 <= point.y && y2 >= point.y)) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static Object clone(Object objet) throws Exception {
        Class eclass = objet.getClass();
        Field[] fields = eclass.getDeclaredFields();
        Object result = eclass.newInstance();
        if (result == null) {
            new NullPointerException();
        }
        for (Field f : fields) {
            String[] tab = f.getName().split(".");
            if (tab.length > 0 ? !tab[tab.length - 1].equals("serialVersionUID") : true) {
                try {
                    f.setAccessible(true);
                    f.set(result, f.get(objet));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    public static void clone(Object cible, Object source) {
        try {
            Field[] lf0 = cible.getClass().getDeclaredFields();
            Class t = source.getClass();
            Field[] lf = t.getDeclaredFields();
            int i = 0;
            for (Field f : lf) {
                String[] tab = f.getName().split(".");
                if (tab.length > 0 ? !tab[tab.length - 1].equals("serialVersionUID") : true) {
                    try {
                        lf0[i].setAccessible(true);
                        f.setAccessible(true);
                        lf0[i].set(cible, f.get(source));
                        i++;
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void controlPoint(Separator separateur, final Point point, int width, int height) {
        try {
            Mur mur = separateur.getMurGauche();
            mur.setB(separateur.getMurDroite().getB());
            mur.setC(separateur.getMurDroite().getC());
            controlPoint(mur, point, width, height);
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void controlPoint(Mur mur, final Point point, int width, int height) {
        try {
            if (point == null) {
                return;
            }
            if (point.x < mur.getA().x) {
                point.x = mur.getA().x;
            }
            if (point.x + width > mur.getB().x) {
                point.x = mur.getB().x - width;
            }
            if (point.y < mur.getA().y) {
                point.y = mur.getA().y;
            }
            if (point.y + height > mur.getD().y) {
                point.y = mur.getD().y - height;
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateMursWhenUpdateSeparator(Separator current, Point point, Salle salle) {
        try {
            IDrawing drawing = Constantes.MAIN.getDisplay().getDrawing();
            boolean controle = drawing != null ? drawing instanceof DrawingPlan : false;

            Utils.controlPoint(current, point, salle.getSeparator(), salle.getWeight());
            Cote cote = current.getCote();

            Mur murGauche = current.getMurGauche();
            int width = (controle && cote.isVertical()) ? (point.y - murGauche.getA().y) : (point.x - murGauche.getA().x);
            murGauche.setWidth(width);

            Mur murDroite = current.getMurDroite();
            width = murDroite.getWidth() + ((controle && cote.isVertical()) ? (murDroite.getA().y - point.y) : (murDroite.getA().x - point.x)) - salle.getSeparator();
            murDroite.setWidth(width);
            Point A = (controle && cote.isVertical()) ? new Point(point.x, murDroite.getA().y + salle.getSeparator()) : new Point(point.x + salle.getSeparator(), murDroite.getA().y);
            murDroite.setA(A);
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static File onGenerateXML(File file, Salle item) {
        try {
            JAXBContext context = JAXBContext.newInstance(Salle.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
            m.marshal(item, file);
        } catch (JAXBException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public static File onGenerateJSON(File file, Salle item) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, item);
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public static String getStringFromObject(Object item) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(item);
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Object getObjectFromString(String item, Class cClass) {
        try {
            return Constantes.GSON.fromJson(item, cClass);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
