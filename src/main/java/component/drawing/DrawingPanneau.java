package component.drawing;

import component.Element;
import component.Salle;
import component.element.Accessoire;
import component.element.Mur;
import component.element.accessoire.RetourAir;
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
    int angleX;
    int angleY;

    boolean vertical = false;

    DrawingElevation.ActionDrawing listener;

    public DrawingPanneau(Mur element, int width, int height) {
        super(element);
        this.element = element;
        this.width = width;
        this.height = height;
        int length = 10;
        angleX = (int) Math.round(Math.cos(Math.toRadians(Config.angleCoinPli)) * length);
        angleY = (int) Math.round(Math.sin(Math.toRadians(Config.angleCoinPli)) * length);
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

    @Override
    public void setObjet(Object objet) {
        this.element = (Mur) objet;
    }

    public Dimension getDimension() {
        try {
            int weigth = element.getCote().getSalle().getWeight();
            vertical = !((element.getWidth() + weigth + Config.pliHeight) > (width / 2));

            if (!vertical) {
                height = ((element.getCote().getSalle().getHeight() + weigth + Config.pliHeight) * 2) + 200;
            }

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

    private void drawAccessoire(Graphics2D g, Accessoire item, int x, int y, int[] pointX, int[] pointY, boolean interne) {
        try {
            g.setStroke(new BasicStroke(1.5f));
            g.setColor(Color.BLACK);
            int[] pointsX, pointsY;
            if (interne && item instanceof RetourAir) {
                int weigth = element.getCote().getSalle().getWeight();

                pointsX = new int[4];
                pointsX[0] = (x + (element.getWidth() / 2) - (item.getWidth() / 2));
                pointsX[1] = pointsX[0] + item.getWidth();
                pointsX[2] = pointsX[1];
                pointsX[3] = pointsX[0];
                /**
                 * Determination des points y de l'element
                 */
                pointsY = new int[4];
                pointsY[0] = (y + (weigth / 2) - (Config.retourWeight / 2));
                pointsY[1] = pointsY[0];
                pointsY[2] = pointsY[0] + Config.retourWeight;
                pointsY[3] = pointsY[2];
            } else {
                int dimsX = item.getA().x - item.getMur().getA().x;
                int dimsY = item.getA().y - item.getMur().getA().y;

                int x1 = pointX[0] + dimsX;
                int x2 = x1 + item.getWidth();
                pointsX = new int[]{x1, x2, x2, x1};
                int y1 = pointY[0] + dimsY;
                int y2 = y1 + item.getHeight();
                pointsY = new int[]{y1, y1, y2, y2};
            }
            g.drawPolygon(pointsX, pointsY, 4);
        } catch (Exception ex) {
            Logger.getLogger(DrawingPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawMur(Graphics2D g, int x, int y, boolean interne) {
        try {
            g.setStroke(new BasicStroke(1.5f));
            g.setColor(Color.BLACK);

            Point origine = element.getCote().getSalle().getOrigin();
            int x1 = x + origine.x;
            int[] pointsX = new int[]{x1, (x1 + element.getWidth()), (x1 + element.getWidth()), x1};
            int[] pointsY = new int[]{y, y, (y + element.getCote().getSalle().getHeight()), (y + element.getCote().getSalle().getHeight())};
            g.drawPolygon(pointsX, pointsY, 4);
            if (interne) {
                drawInterne(g, x1, y, pointsX, pointsY);
            } else {
                drawExterne(g, x1, y, pointsX, pointsY);
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawInterne(Graphics2D g, int x, int y, int[] pointX, int[] pointY) {
        try {
            g.setStroke(new BasicStroke(0.75f));

            int decale = element.getCote().getSalle().getDecaleEpaisseurMur();
            int epaisseur = element.getCote().getSalle().getWeight();

            int x1 = x + decale;
            int x2 = x + element.getWidth();
            int[] pointsX = new int[]{x1, (x2 - decale), (x2 - decale), x1};
            int y1 = y - epaisseur;
            int[] pointsY = new int[]{y1, y1, y, y};
            g.setColor(Color.BLUE);
            g.drawPolygon(pointsX, pointsY, 4);

            for (Accessoire acc : element.getAccessoires()) {
                drawAccessoire(g, acc, pointsX[0], pointsY[0], pointX, pointY, true);
            }

            pointsX = new int[]{pointsX[0] + angleX, pointsX[1] - angleX, pointsX[2], pointsX[3]};
            pointsY = new int[]{pointsY[0] - Config.pliHeight, pointsY[1] - Config.pliHeight, y1 - 1, y1 - 1};
            g.setColor(Color.ORANGE);
            g.drawPolygon(pointsX, pointsY, 4);

            y += element.getCote().getSalle().getHeight() + epaisseur;
            y1 = y - epaisseur;
            pointsX = new int[]{x1, (x2 - decale), (x2 - decale), x1};
            pointsY = new int[]{y1, y1, y, y};
            g.setColor(Color.BLUE);
            g.drawPolygon(pointsX, pointsY, 4);

            pointsX = new int[]{pointsX[0], pointsX[1], pointsX[2] - angleX, pointsX[3] + angleX};
            pointsY = new int[]{y + 1, y + 1, y + Config.pliHeight, y + Config.pliHeight};
            g.setColor(Color.ORANGE);
            g.drawPolygon(pointsX, pointsY, 4);

        } catch (Exception ex) {
            Logger.getLogger(DrawingPanneau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawExterne(Graphics2D g, int x, int y, int[] pointX, int[] pointY) {
        try {
            g.setStroke(new BasicStroke(0.75f));

            int decale = element.getCote().getSalle().getDecaleEpaisseurMur();
            int epaisseur = element.getCote().getSalle().getWeight();
            int x1 = x - epaisseur;
            int[] pointsX = new int[]{x1, x, x, x1};
            int y1 = y + decale;
            int y2 = y + element.getCote().getSalle().getHeight();
            int[] pointsY = new int[]{y1, y1, (y2 - decale), (y2 - decale)};
            g.setColor(Color.BLUE);
            g.drawPolygon(pointsX, pointsY, 4);

            for (Accessoire acc : element.getAccessoires()) {
                drawAccessoire(g, acc, pointsX[0], pointsY[0], pointX, pointY, false);
            }

            pointsX = new int[]{(pointsX[0] - Config.pliHeight), (pointsX[0] + 1), (pointsX[0] + 1), (pointsX[0] - Config.pliHeight)};
            pointsY = new int[]{pointsY[0] + angleX, pointsY[1], pointsY[2], (pointsY[3] - angleX)};
            g.setColor(Color.ORANGE);
            g.drawPolygon(pointsX, pointsY, 4);

            x += element.getWidth();
            x1 = x + epaisseur;
            pointsX = new int[]{x, x1, x1, x + 1};
            pointsY = new int[]{y1, y1, (y2 - decale), (y2 - decale)};
            g.setColor(Color.BLUE);
            g.drawPolygon(pointsX, pointsY, 4);

            pointsX = new int[]{pointsX[1] + 1, (pointsX[1] + Config.pliHeight), (pointsX[2] + Config.pliHeight), pointsX[2] + 1};
            pointsY = new int[]{pointsY[0], pointsY[1] + angleX, (pointsY[2] - angleX), pointsY[3]};
            g.setColor(Color.ORANGE);
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
            if (!vertical) {
                g.drawLine(0, (height / 2), width, (height / 2));

                drawMur(g, 0, y / 4, false);
                drawMur(g, 0, ((y / 4) + (height / 2)), true);
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
