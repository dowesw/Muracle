/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.drawing;

import java.awt.*;

import component.Salle;
import component.element.accessoire.RetourAir;
import tools.Config;
import tools.Point;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

import component.element.Accessoire;
import component.element.Cote;
import component.Element;
import component.element.Mur;
import component.element.Separator;
import gui.JBloc;
import tools.Utils;
import gui.JAccordion;

/**
 * @author dowes
 */
public class DrawingElevation extends ADrawing implements IDrawing {

    boolean interne = true;

    /**
     * Le coté qu'il faut désiner
     */
    Cote element;

    Element itemSelected = null;

    ActionDrawing listener;

    /**
     * Le constructeur qui prend en paramètre le coté d'un faut désiner
     *
     * @param element
     * @param interne
     */
    public DrawingElevation(Cote element, boolean interne) {
        super(element);
        this.element = element;
        this.interne = interne;
    }

    public DrawingElevation(Cote element, boolean interne, ActionDrawing listener) {
        this(element, interne);
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

    @Override
    public void setObjet(Object objet) {
        this.element = (Cote) objet;
    }

    public boolean isInterne() {
        return interne;
    }

    public Dimension getDimension() {
        try {
            int width = element.getSalle().getLength() + (element.getSalle().getOrigin().getX() * 2);
            int height = element.getSalle().getHeight() + (element.getSalle().getOrigin().getY() * 2);
            return new Dimension(width, height);
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Fonction qui trouve l'élément ou le trouve le point du curseur
     *
     * @param point
     */
    @Override
    public Element findElement(Point point) {
        Element result = null;
        if (point != null) {
            for (Element item : element.getElements()) {
                item.setSelected(Utils.checkPointInElement(item, point));
                if (item.isSelected()) {
                    result = item;
                }
                if (item instanceof Mur) {
                    Mur mur = ((Mur) item);
                    for (Accessoire acc : (interne ? mur.getAccessoiresInterne() : mur.getAccessoiresExterne())) {
                        acc.setSelected(Utils.checkPointInElement(acc, point));
                        if (acc.isSelected()) {
                            if (result != null) {
                                int index = element.getElements().indexOf(result);
                                if (index > -1) {
                                    element.getElements().get(index).setSelected(false);
                                }
                            }
                            result = acc;
                        }
                    }
                    int index = element.getElements().indexOf(mur);
                    if (index > -1) {
                        element.getElements().set(index, mur);
                    }
                }
            }
        }
        this.itemSelected = result;
        return result;
    }

    private void drawAccessoire(Graphics g, Mur mur, Accessoire item) {
        try {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2.5f));
            /**
             * Determination des points x de l'element
             */
            int[] x = new int[4];
            x[0] = item instanceof RetourAir ? (mur.getA().x + (mur.getWidth() / 2) - (item.getWidth() / 2)) : item.getA().x;
            x[1] = x[0] + item.getWidth();
            x[2] = x[1];
            x[3] = x[0];
            /**
             * Determination des points y de l'element
             */
            int[] y = new int[4];
            y[0] = item instanceof RetourAir ? (mur.getD().y - item.getHeight() - Config.retourDistanceSol) : item.getA().y;
            y[1] = y[0];
            y[2] = y[0] + item.getHeight();
            y[3] = y[2];

            g2d.drawPolygon(x, y, 4);

            /**
             * Définition des différents points de l'élément
             */
            item.setA(new Point(x[0], y[0]));
            item.setB(new Point(x[1], y[1]));
            item.setC(new Point(x[2], y[2]));
            item.setD(new Point(x[3], y[3]));
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Point drawElement(Graphics g, Element item) {
        Point point = null;
        try {
            /**
             * Determination des points x de l'element
             */
            int[] x = new int[4];
            x[0] = item.getA().x;
            x[1] = x[0] + (item instanceof Mur ? ((Mur) item).getWidth() : element.getSalle().getSeparator());
            x[2] = x[1];
            x[3] = x[0];
            /**
             * Determination des points y de l'element
             */
            int[] y = new int[4];
            y[0] = item.getA().y;
            y[1] = y[0];
            y[2] = y[0] + element.getSalle().getHeight();
            y[3] = y[2];

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
            if (item instanceof Mur) {
                for (Accessoire acc : (interne ? ((Mur) item).getAccessoiresInterne() : ((Mur) item).getAccessoiresExterne())) {
                    drawAccessoire(g, (Mur) item, acc);
                }
            }
            /**
             * Determination du point d'origine du prochain élément
             */
            point = item.getB();
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return point;

    }

    @Override
    public void draw(Graphics gs) {
        try {
            Graphics2D g = (Graphics2D) gs;
            g.setStroke(new BasicStroke(1.5f));
            g.setColor(Color.BLACK);
            /**
             * Définition du point d'origine
             */
            Point point = element.getSalle().getOrigin();

            Element selected = null;
            Mur murSelected = null;
            for (int i = 0; i < element.getElements().size(); i++) {
                Element item = element.getElements().get(i);
                item.setA(point);
                /**
                 * Détermination si le mur est le premier ou le dernier
                 */
                if (item instanceof Mur) {
                    ((Mur) item).setFirst(i == 0);
                    ((Mur) item).setLast(i == element.getElements().size() - 1);
                }
                point = drawElement(g, item);
                element.getElements().set(i, item);
                /**
                 * Récupération du mur selectionné (ceci pour pour changer la
                 * bordure entiere du mur)
                 */
                if (item.isSelected()) {
                    selected = item;
                }
                if (item instanceof Mur) {
                    for (Accessoire acc : ((Mur) item).getAccessoires()) {
                        if (acc.isSelected()) {
                            selected = acc;
                            murSelected = (Mur) item;
                        }
                    }
                }
            }
            if (selected != null) {
                g.setColor(Color.RED);
                if (selected instanceof Accessoire) {
                    drawAccessoire(g, murSelected, (Accessoire) selected);
                } else {
                    drawElement(g, selected);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingElevation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fonction qui défini le panel de controle de l'afficheur
     *
     * @param panel
     */
    @Override
    public void manager(JPanel panel) {
        try {
            JButton back = new JButton("Retour");
            back.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (listener != null) {
                        listener.back();
                    }
                }
            });
            JBloc cote = new JBloc(panel.getPreferredSize().width, 140, 103, "Coté");
            cote.setContainer(element.properties(getListerner()));
            JBloc item = new JBloc(panel.getPreferredSize().width, 155, 119, "Mur");
            item.setVisible(false);
            if (itemSelected != null) {
                if (itemSelected instanceof Separator) {
                    item = new JBloc(panel.getPreferredSize().width, 92, 55, "Séparateur");
                } else if (itemSelected instanceof Accessoire) {
                    item = new JBloc(panel.getPreferredSize().width, 155, 119, itemSelected.getClass().getSimpleName());
                }
                item.setContainer(itemSelected.properties(getListerner()));
                item.setVisible(true);
            }

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            panel.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            panel.repaint();

            new JAccordion(panel, new JAccordion.Panel[]{new JAccordion.Panel(cote), new JAccordion.Panel(item)});
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public interface ActionDrawing {

        void back();
    }
}
