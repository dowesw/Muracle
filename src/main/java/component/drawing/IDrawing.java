/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.drawing;

import java.awt.*;

import tools.Point;

import javax.swing.*;

import component.Element;

/**
 * @author dowes
 */
public interface IDrawing {

    /**
     * Fonction qui dessine
     *
     * @param g
     */
    void draw(Graphics g);

    /**
     * Fonction qui return l'objet qui est déssiné
     *
     * @return l'objet qui est déssiné
     */
    Object getObjet();

    /**
     * Fonction qui trouve l'élément ou le trouve le point du curseur
     *
     * @param point
     * @return l'élément ou se trouve le point
     */
    Element findElement(Point point);

    /**
     * Fonction qui défini le panel de controle de l'afficheur
     *
     * @param panel
     */
    void manager(JPanel panel);

    Dimension getDimension();

    /**
     * Fonction pour définir la méthode d'actualisation après changement
     *
     * @param listerner
     */
    void setListerner(IDrawing.Listerner listerner);

    public interface Listerner {

        void refresh();
    }
}
