/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import component.drawing.DrawingPanneau;
import component.element.accessoire.RetourAir;
import domain.ActionManager;
import tools.Point;
import component.element.Accessoire;
import component.element.Cote;
import component.Element;
import component.element.Mur;
import component.Salle;
import component.element.Separator;
import component.drawing.DrawingElevation;
import component.drawing.DrawingPlan;
import component.drawing.IDrawing;
import domain.Controller;
import tools.Messages;
import tools.Utils;
import view.FrameMain;

/**
 * @author dowes
 */
public class DrawingPanel extends JPanel {

    FrameMain frame;
    IDrawing drawing;

    Point pointPressed = null;
    Element elementSelected;

    boolean pressed = false;

    public DrawingPanel(FrameMain frame) {
        this.frame = frame;
        initComponent();
    }

    private ActionManager getActionManager() {
        return drawing != null ? drawing.getActionManager() : null;
    }

    public FrameMain getFrame() {
        return frame;
    }

    public IDrawing getDrawing() {
        return drawing;
    }

    public JPanel getTools() {
        return getFrame().getTools();
    }

    public Controller getControler() {
        return getFrame().getControler();
    }

    private void initComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                drawingMousePressed(evt);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                drawingMouseReleased(evt);
            }
        });
    }

    public void setDrawing(final IDrawing drawing) {
        this.drawing = drawing;
        this.drawing.setListerner(new IDrawing.Listerner() {

            @Override
            public void refresh() {
                repaint();
            }
        });
        setPreferredSize(drawing.getDimension());
        this.repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        try {
            removeAll();
            if (getControler().isDisplayGrille()) {
                getControler().drawGrille(g, getWidth(), getHeight());
            }
            if (drawing != null) {
                drawing.draw(g);
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fonction qui trouve l'??l??ment ou le trouve le point du curseur
     *
     * @param point
     * @return
     */
    public Element findElement(Point point) {
        Element element = null;
        try {
            if (drawing != null) {
                element = drawing.findElement(point);
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return element;
    }

    public void manager(JPanel panel) {
        if (drawing != null) {
            panel.removeAll();
            drawing.manager(panel);
        }
    }

    public void undo() {
        drawing.undo();
        repaint();
    }

    public void redo() {
        drawing.redo();
        repaint();
    }

    public boolean displayUndo() {
        return getActionManager().displayUndo();
    }

    public boolean displayRedo() {
        return getActionManager().displayRedo();
    }

    public void drawSalle(final Salle salle) {
        final JPanel tools = getFrame().getTools();
        setDrawing(new DrawingPlan(salle, new DrawingPlan.ActionDrawing() {
            @Override
            public void refresh() {
                drawSalle(salle);
            }
        }));
        manager(tools);
    }

    public void drawCote(final Cote cote, boolean interne) {
        try {
            getControler().setCote(cote);
            if (cote != null) {
                final JPanel tools = getFrame().getTools();
                setDrawing(new DrawingElevation(cote, interne, new DrawingElevation.ActionDrawing() {
                    @Override
                    public void back() {
                        drawSalle(getControler().getSalle());
                    }
                }));
                manager(tools);
            } else {
                Messages.error(getFrame(), "Error", "Vous devez selectionner un cote");
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void drawMur(final Mur mur) {
        try {
            getControler().setMur(mur);
            if (mur != null) {
                final JPanel tools = getFrame().getTools();
                setDrawing(new DrawingPanneau(mur, getWidth(), getHeight(), new DrawingElevation.ActionDrawing() {
                    @Override
                    public void back() {
                        drawCote(getControler().getCote(), true);
                    }
                }));
                manager(tools);
            } else {
                Messages.error(getFrame(), "Error", "Vous devez selectionner un mur");
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addSeparator(Cote cote, final Point point) {
        try {
            if (getControler().addSeparator(cote, point)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addSeparator(Cote cote, Mur mur, final Point point) {
        try {
            if (getControler().addSeparator(mur, cote, point)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPorte(Mur mur, final Point point) {
        try {
            if (getControler().addPorte(mur, point)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addFenetre(Mur mur, final Point point) {
        try {
            if (getControler().addFenetre(mur, point)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPrise(Mur mur, final Point point) {
        try {
            if (getControler().addPrise(mur, point)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addRetour(Mur mur, final Point point) {
        try {
            if (getControler().addRetour(mur, point)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeAccessoire(Accessoire element) {
        try {
            if (getControler().removeAccessoire(element)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeSeparator(Separator element) {
        try {
            if (getControler().removeSeparator(element)) {
                getActionManager().action();
                repaint();
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawingMouseReleased(MouseEvent evt) {
        try {
            pressed = false;
            Point point = new Point(evt.getPoint().x, evt.getPoint().y);
            Point pointReleased = getControler().getPoint(point, 1);
            if (pointPressed != null && pointReleased != null) {
                if (elementSelected != null ? (elementSelected instanceof Accessoire || elementSelected instanceof Separator) : false) {
                    if (elementSelected instanceof RetourAir) {
                        return;
                    }
                    if (pointPressed.x != pointReleased.x || pointPressed.y != pointReleased.y) {
                        if (elementSelected instanceof Accessoire) {
                            Accessoire item = ((Accessoire) elementSelected);
                            Mur mur = item.getMur();
                            Utils.controlPoint(mur, pointReleased, item.getWidth(), item.getHeight());
                        }
                        if (elementSelected instanceof Separator) {
                            Separator item = ((Separator) elementSelected);
                            Utils.updateMursWhenUpdateSeparator(item, pointReleased, getControler().getSalle());
                        }
                        elementSelected.setA(pointReleased);
                        getActionManager().action();
                        repaint();
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawingMousePressed(MouseEvent evt) {
        try {
            pressed = true;
            Point point = new Point(evt.getPoint().x, evt.getPoint().y);
            pointPressed = getControler().getPoint(point, 1);
            elementSelected = findElement(pointPressed);
            if (SwingUtilities.isRightMouseButton(evt) && elementSelected != null) {
                JPopupMenu context = null;
                if (getDrawing() instanceof DrawingPlan) {
                    context = createMenuDrawingPlan(elementSelected);
                } else if (getDrawing() instanceof DrawingElevation) {
                    context = createMenuDrawingElevation(elementSelected);
                }
                if (context == null) {
                    return;
                }
                context.show(getFrame(), evt.getXOnScreen(), evt.getYOnScreen());
            } else {
                manager(getFrame().getTools());
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JPopupMenu createMenuDrawingPlan(final Element elementSelected) {
        JPopupMenu context = null;
        try {
            context = new JPopupMenu("Options");
            JMenuItem separator = new JMenuItem("Ajouter un s??parateur");
            separator.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (elementSelected instanceof Cote) {
                        addSeparator((Cote) elementSelected, pointPressed);
                    }
                }
            });
            context.add(separator);
            context.add(new JSeparator());
            JMenuItem interne = new JMenuItem("Affichage vue interne");
            interne.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (elementSelected instanceof Cote) {
                        drawCote((Cote) elementSelected, true);
                    } else {
                        drawCote(null, false);
                    }
                }
            });
            context.add(interne);
            JMenuItem externe = new JMenuItem("Affichage vue externe");
            externe.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (elementSelected instanceof Cote) {
                        drawCote((Cote) elementSelected, false);
                    } else {
                        drawCote(null, false);
                    }
                }
            });
            context.add(externe);
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return context;
    }

    private JPopupMenu createMenuDrawingElevation(final Element elementSelected) {
        JPopupMenu context = null;
        try {
            context = new JPopupMenu("Options");
            if (elementSelected instanceof Separator || elementSelected instanceof Accessoire) {
                JMenuItem delete = new JMenuItem("Supprimer");
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (elementSelected instanceof Accessoire) {
                            removeAccessoire((Accessoire) elementSelected);
                        } else if (elementSelected instanceof Separator) {
                            removeSeparator((Separator) elementSelected);
                        }
                    }
                });
                context.add(delete);
            } else {
                if (!((DrawingElevation) getDrawing()).isInterne()) {
                    return null;
                }
                JMenuItem separator = new JMenuItem("Ajouter un s??parateur");
                separator.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (elementSelected instanceof Mur) {
                            addSeparator(getControler().getCote(), (Mur) elementSelected, pointPressed);
                        }
                    }
                });
                context.add(separator);
                context.add(new JSeparator());
                JMenuItem porte = new JMenuItem("Ajouter une porte");
                porte.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (elementSelected instanceof Mur) {
                            addPorte((Mur) elementSelected, pointPressed);
                        }
                    }
                });
                context.add(porte);
                JMenuItem fenetre = new JMenuItem("Ajouter une fenetre");
                fenetre.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (elementSelected instanceof Mur) {
                            addFenetre((Mur) elementSelected, pointPressed);
                        }
                    }
                });
                context.add(fenetre);
                JMenuItem prise = new JMenuItem("Ajouter une prise");
                prise.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (elementSelected instanceof Mur) {
                            addPrise((Mur) elementSelected, pointPressed);
                        }
                    }
                });
                context.add(prise);
                JMenuItem retourAir = new JMenuItem("Ajouter un retour d'air");
                retourAir.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (elementSelected instanceof Mur) {
                            addRetour((Mur) elementSelected, pointPressed);
                        }
                    }
                });
                context.add(retourAir);
                context.add(new JSeparator());
                JMenuItem displayPanneau = new JMenuItem("Afficher les panneaux");
                displayPanneau.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (elementSelected instanceof Mur) {
                            drawMur((Mur) elementSelected);
                        }
                    }
                });
                context.add(displayPanneau);
            }
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return context;
    }

}
