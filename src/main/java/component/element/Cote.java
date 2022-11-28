/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import component.Element;
import component.Salle;
import tools.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.annotation.*;

import component.drawing.IDrawing;
import tools.Utils;
import tools.adapter.ConvertSalle;

/**
 * @author dowes
 */
@XmlRootElement(name = "cote")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cote extends Element {

    /**
     * Indication la position du cote NORD, SUD, EST, OUEST
     */
    @XmlElement(name = "position")
    private Position position;
    /**
     * La salle dont appartient le coté
     */
    @XmlTransient
    @JsonSerialize(converter = ConvertSalle.class)
    private Salle salle;
    /**
     * La liste des elements du coté
     */
    @XmlElementWrapper(name = "elements")
    @XmlElement(name = "element")
    private List<Element> elements = new ArrayList<>();

    private Cote() {
        super();
    }

    public Cote(long id, Salle salle, Position position) {
        this(id, "Coté N°" + id, salle, position);
    }

    public Cote(long id, String name, Salle salle, Position position) {
        super(id, name);
        this.salle = salle;
        this.position = position;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public boolean isVertical() {
        return position.equals(Position.EAST) || position.equals(Position.WEST);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonIgnore
    public List<Mur> getMurs() {
        List<Mur> result = new ArrayList<>();
        for (Element item : elements) {
            if (item instanceof Mur) {
                result.add((Mur) item);
            }
        }
        return result;
    }

    @JsonIgnore
    @Override
    public JTable properties(final IDrawing.Listerner listener) {
        JTable table = new javax.swing.JTable();
        try {
            final DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                    new Object[][]{
                            {"Name", getName()},
                            {"Point A", "[" + getA().x + ", " + getA().y + "]"},
                            {"Point B", "[" + getB().x + ", " + getB().y + "]"},
                            {"Point C", "[" + getC().x + ", " + getC().y + "]"},
                            {"Point D", "[" + getD().x + ", " + getD().y + "]"},
                            {"Nombre de mûr", getMurs().size()}},
                    new String[]{"", ""}
            ) {
                boolean[] canEdit = new boolean[]{false, false};

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            table.setModel(model);
            model.addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent event) {
                    try {
                        if (event.getType() == TableModelEvent.UPDATE) {
                            Object value = model.getValueAt(event.getFirstRow(), event.getColumn());
                            if (value != null ? value.toString().trim().isEmpty() : true) {
                                return;
                            }
                            switch (event.getFirstRow()) {
                                case 0: {//Name
                                    System.out.println("Name : " + value);
                                    break;
                                }
                                case 1: {//Dimension
                                    System.out.println("Dimension : " + value);
                                    break;
                                }
                                case 2: {//Nombre de mûr
                                    System.out.println("Nombre de mûr : " + value);
                                    break;
                                }
                            }
                            if (listener != null) {
                                listener.refresh();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(Cote.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Cote.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    @JsonIgnore
    public Cote build() {
        try {
            int width = isVertical() ? getSalle().getWeight() : getSalle().getWidth();
            int height = isVertical() ? getSalle().getWidth() : getSalle().getWeight();
            setB(new Point(getA().x + width, getA().y));
            setC(new Point(getB().x, getB().y + height));
            setD(new Point(getA().x, getC().y));
        } catch (Exception ex) {
            Logger.getLogger(Cote.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    @JsonIgnore
    public Cote clone() {
        Cote result = new Cote();
        Utils.clone(result, this);
        result.setElements(new ArrayList<>(getElements()));
        return result;
    }

    public enum Position {

        NORTH, SOUTH, EAST, WEST
    }
}
