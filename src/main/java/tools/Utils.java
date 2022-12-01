/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import component.element.Mur;
import component.element.Separator;
import domain.Controller;
import tools.Point;
import component.Element;

import java.awt.*;
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

    public static void clone(Object cible, Object source) {
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
    }

    public static void controlPoint(Separator separateur, final Point point, int width, int height) {
        Mur mur = separateur.getMurGauche();
        mur.setB(separateur.getMurDroite().getB());
        mur.setC(separateur.getMurDroite().getC());
        controlPoint(mur, point, width, height);
    }

    public static void controlPoint(Mur mur, final Point point, int width, int height) {
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
    }

}
