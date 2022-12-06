package component.drawing;

import component.Element;
import component.Salle;
import component.element.Mur;
import gui.JAccordion;
import gui.JBloc;
import tools.Config;
import tools.Point;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrawingPanneau extends ADrawing implements IDrawing {

    /**
     * La mur qu'il faut désiner
     */
    Mur element;

    int width;
    int height;

    DrawingElevation.ActionDrawing listener;

    public DrawingPanneau(Mur element, int width, int height) {
        this.element = element;
        this.width = width;
        this.height = height;
    }

    public DrawingPanneau(Mur element, int width, int height, DrawingElevation.ActionDrawing listener) {
        this(element, width, height);
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

    public Dimension getDimension() {
        try {
            int width = 1000;
            int height = 1000;
            return new Dimension(width, height);
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public Element findElement(Point point) {
        try {

        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void drawMur(Graphics2D g, int x, int y, boolean interne) {
        try {
            g.setStroke(new BasicStroke(1.5f));
            g.setColor(Color.BLACK);

            Point origine = element.getCote().getSalle().getOrigin();
            int[] pointsX = new int[]{(x + origine.x), (x + origine.x + element.getWidth()), (x + origine.x + element.getWidth()), (x + origine.x)};
            int[] pointsY = new int[]{y, y, (y + element.getCote().getSalle().getHeight()), (y + element.getCote().getSalle().getHeight())};
            g.drawPolygon(pointsX, pointsY, 4);
            if (interne) {
                drawInterne(g, (x + origine.x), y);
            } else {
                drawExterne(g, (x + origine.x), y);
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawInterne(Graphics2D g, int x, int y) {
        try {
            g.setStroke(new BasicStroke(0.75f));
            g.setColor(Color.BLUE);

            int decale = element.getCote().getSalle().getDecaleEpaisseurMur();
            int epaisseur = element.getCote().getSalle().getWeight();
            int[] pointsX = new int[]{(x + decale), (x + element.getWidth() - (decale)), (x + element.getWidth() - (decale)), (x + decale)};
            int[] pointsY = new int[]{y - epaisseur, y - epaisseur, y, y};
            g.drawPolygon(pointsX, pointsY, 4);

            y += element.getCote().getSalle().getHeight() + epaisseur;
            pointsX = new int[]{(x + decale), (x + element.getWidth() - (decale)), (x + element.getWidth() - (decale)), (x + decale)};
            pointsY = new int[]{y - epaisseur, y - epaisseur, y, y};
            g.drawPolygon(pointsX, pointsY, 4);
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawExterne(Graphics2D g, int x, int y) {
        try {
            g.setStroke(new BasicStroke(0.75f));
            g.setColor(Color.BLUE);

            int decale = element.getCote().getSalle().getDecaleEpaisseurMur();
            int epaisseur = element.getCote().getSalle().getWeight();
            int[] pointsX = new int[]{(x - epaisseur), (x), (x), (x - epaisseur)};
            int[] pointsY = new int[]{y + decale, y + decale, (y + element.getCote().getSalle().getHeight() - decale), (y + element.getCote().getSalle().getHeight() - decale)};
            g.drawPolygon(pointsX, pointsY, 4);

            x += element.getWidth();
            pointsX = new int[]{x, (x + epaisseur), (x + epaisseur), x};
            pointsY = new int[]{y + decale, y + decale, (y + element.getCote().getSalle().getHeight() - decale), (y + element.getCote().getSalle().getHeight() - decale)};
            g.drawPolygon(pointsX, pointsY, 4);
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(Graphics gs) {
        try {
            Graphics2D g = (Graphics2D) gs;

            int y = (height / 2) - (element.getCote().getSalle().getHeight() / 2);
            int weigth = element.getCote().getSalle().getWeight();
            if ((element.getWidth() + weigth + Config.pliHeight) > (width / 2)) {
                g.drawLine(0, (height / 2), width, (height / 2));

                drawMur(g, 0, y/4, false);
                drawMur(g, 0, ((y/4)+(height / 2)), true);
            } else {
                g.drawLine((width / 2), 0, (width / 2), height);
                int x = (width / 2);

                drawMur(g, 0, y, false);
                drawMur(g, x, y, true);
            }

        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            JBloc mur = new JBloc(panel.getPreferredSize().width, 155, 119, "Mur");
            mur.setContainer(element.properties(getListerner()));


            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            panel.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(mur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            );
            panel.repaint();
            new JAccordion(panel, new JAccordion.Panel[]{new JAccordion.Panel(mur)});
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
