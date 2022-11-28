/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.drawing;

import tools.Point;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import component.element.Cote;
import component.Element;
import component.element.Mur;
import component.Salle;
import gui.JBloc;
import tools.Utils;
import gui.JAccordion;

/**
 *
 * @author dowes
 */
public class DrawingPlan extends ADrawing implements IDrawing {

    /**
     * La salle qu'il faut désiner
     */
    Salle element;
    /**
     * Le coté selectionné depuis la vue
     */
    Cote coteSelected;

    ActionDrawing listener;

    public DrawingPlan(Salle element, ActionDrawing listener) {
        this.element = element;
        this.listener = listener;
    }

    @Override
    public void setListerner(Listerner listerner) {
        this.listerner = listerner;
    }

    @Override
    public Object getObjet() {
        return element;
    }

    /**
     * Methode qui désinne un mur
     *
     * @param g Graphics ou le mur ou le séparateur est déssiné
     * @param cote Le coté ou se trouve le mur ou le séparateur
     * @param index La position du coté
     * @param item Le mur ou le separateur qui sera déssiné
     * @return Le point d'origine du prochain élément qui sera déssiné
     */
    private Point drawElement(Graphics g, Cote cote, int index, Element item) {
        Point point = null;
        try {
            /**
             * Determination des points x de l'element
             */
            int[] x = new int[4];
            x[0] = item.getA().x;
            if (item instanceof Mur) {
                x[1] = x[0] + (cote.isVertical() ? element.getWeight() : ((Mur) item).getWidth());
                x[2] = x[1];
                x[3] = x[0];
                /**
                 * Determination de l'inclinaison des murs de coin
                 */
                if (index == 0) {
                    if (((Mur) item).isFirst()) {
                        x[3] += element.getWeight();
                    }
                    if (((Mur) item).isLast()) {
                        x[2] -= element.getWeight();
                    }
                } else if (index == 2) {
                    if (((Mur) item).isFirst()) {
                        x[0] += element.getWeight();
                    }
                    if (((Mur) item).isLast()) {
                        x[1] -= element.getWeight();
                    }
                }
            } else {
                x[1] = x[0] + (cote.isVertical() ? element.getWeight() : element.getSeparator());
                x[2] = x[1];
                x[3] = x[0];
            }
            /**
             * Determination des points y de l'element
             */
            int[] y = new int[4];
            y[0] = item.getA().y;
            if (item instanceof Mur) {
                y[1] = y[0];
                y[2] = y[0] + (cote.isVertical() ? ((Mur) item).getWidth() : element.getWeight());
                y[3] = y[2];
                /**
                 * Determination de l'inclinaison des murs de coin
                 */
                if (index == 1) {
                    if (((Mur) item).isFirst()) {
                        y[0] += element.getWeight();
                    }
                    if (((Mur) item).isLast()) {
                        y[3] -= element.getWeight();
                    }
                } else if (index == 3) {
                    if (((Mur) item).isFirst()) {
                        y[1] += element.getWeight();
                    }
                    if (((Mur) item).isLast()) {
                        y[2] -= element.getWeight();
                    }
                }
            } else {
                y[1] = y[0];
                y[2] = y[0] + (cote.isVertical() ? element.getSeparator() : element.getWeight());
                y[3] = y[2];
            }

            if (item instanceof Mur) {
                /**
                 * Dessin du mur
                 */
                g.drawPolygon(x, y, 4);
            } else {
                /**
                 * Dessin du séparateur
                 */
                g.fillPolygon(x, y, 4);
            }
            /**
             * Définition des différents points de l'élément
             */
            item.setB(new Point(x[1], y[1]));
            item.setC(new Point(x[2], y[2]));
            item.setD(new Point(x[3], y[3]));
            /**
             * Determination du point d'origine du prochain élément
             */
            point = cote.isVertical() ? item.getD() : item.getB();
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return point;
    }

    /**
     * Methode qui déssine un coté
     *
     * @param g Graphics ou le coté est déssiné
     * @param cote Le coté qui sera déssiné
     * @param index La position du coté
     * @return Le coté mis à jour
     */
    private void drawCote(Graphics g, Cote cote, int index) {
        try {
            /**
             * Définition du point d'origine
             */
            Point point = cote.getA();
            /**
             * Définition des elements pour déterminer les points du coté
             */
            Mur first = null, last = null;

            for (int i = 0; i < cote.getElements().size(); i++) {
                Element item = cote.getElements().get(i);
                /**
                 * Détermination si le mur est le premier ou le dernier
                 */
                if (item instanceof Mur) {
                    ((Mur) item).setFirst(i == 0);
                    ((Mur) item).setLast(i == cote.getElements().size() - 1);
                }
                if (point == null) {
                    throw new Exception("Le point d'origne ne peu être null");
                }
                item.setA(point);
                point = drawElement(g, cote, index, item);

                if (item instanceof Mur) {
                    if (((Mur) item).isFirst()) {
                        first = ((Mur) item);
                    }
                    if (((Mur) item).isLast()) {
                        last = ((Mur) item);
                    }
                }
            }
            if (!cote.isVertical()) {
                if (first != null) {
                    /**
                     * Définition du point d'angle A du mur
                     */
                    cote.setA(first.getA());
                    /**
                     * Définition du point d'angle D du mur
                     */
                    cote.setD(first.getD());

                }
                if (last != null) {
                    /**
                     * Définition du point d'angle B du mur
                     */
                    cote.setB(last.getB());
                    /**
                     * Définition du point d'angle C du mur
                     */
                    cote.setC(last.getC());
                }
            } else {
                if (first != null) {
                    /**
                     * Définition du point d'angle A du mur
                     */
                    cote.setA(first.getA());
                    /**
                     * Définition du point d'angle B du mur
                     */
                    cote.setB(first.getB());

                }
                if (last != null) {
                    /**
                     * Définition du point d'angle C du mur
                     */
                    cote.setC(last.getC());
                    /**
                     * Définition du point d'angle D du mur
                     */
                    cote.setD(last.getD());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(Graphics gs) {
        try {
            Graphics2D g = (Graphics2D) gs;
            g.setStroke(new BasicStroke(1.5f));
            g.setColor(Color.BLACK);
            /**
             * Récupération des valeurs d'origine x et y
             */
            int x = element.getOrigin().x;
            int y = element.getOrigin().y;

            Cote selected = null;
            Point point;

            for (int i = 0; i < element.getCotes().size(); i++) {
                Cote item = element.getCotes().get(i);
                /**
                 * Calcule du point d'angle A du mur
                 */
                point = new Point(x, y);
                if (i == 1) {
                    point = new Point(element.getCotes().get(0).getB().x - element.getWeight(), element.getCotes().get(0).getB().y);
                } else if (i == 2) {
                    point = new Point(x, element.getCotes().get(1).getC().y - element.getWeight());
                }
                item.setA(point);
                drawCote(g, item, i);

                /**
                 * Récupération du mur selectionné (ceci pour pour changer la
                 * bordure entiere du mur)
                 */
                element.getCotes().set(i, item);
                if (item.isSelected()) {
                    selected = item;
                }

            }
            if (selected != null) {
                g.setColor(Color.RED);
                drawCote(g, selected, element.getCotes().indexOf(selected));
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fonction qui trouve l'élément ou le trouve le point du curseur
     *
     * @param point
     */
    @Override
    public Element findElement(Point point) {
        Element result = null;
        try {
            if (point != null) {
                for (Cote item : element.getCotes()) {
                    item.setSelected(Utils.checkPointInElement(item, point));
                    if (item.isSelected()) {
                        result = item;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.coteSelected = (Cote) result;
        return result;
    }

    /**
     * Fonction qui défini le panel de controle de l'afficheur
     *
     * @param panel
     */
    @Override
    public void manager(JPanel panel) {
        try {
            JBloc salle = new JBloc(panel.getPreferredSize().width, 123, 88, "Salle");
            salle.setContainer(element.properties(getListerner()));
            JBloc cote = new JBloc(panel.getPreferredSize().width, 140, 103, "Coté");
            cote.setVisible(false);
            if (coteSelected != null) {
                cote.setContainer(coteSelected.properties(getListerner()));
                cote.setVisible(true);
            }

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            panel.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(salle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(salle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            panel.repaint();

            new JAccordion(panel, new JAccordion.Panel[]{new JAccordion.Panel(salle), new JAccordion.Panel(cote)});
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public interface ActionDrawing {

        void refresh();
    }

}
