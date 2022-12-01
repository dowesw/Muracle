/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import component.element.Cote;
import component.element.Mur;
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

/**
 * @author dowes
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Salle {

    /**
     * Le point d'origine pour commence le dessin de notre piece
     */
    @XmlElement(name = "point")
    private Point origin = new Point(0, 0);
    /**
     * La hauteur des murs
     */
    @XmlElement(name = "mur-height")
    private int height = 0;
    /**
     * La longueur de la salle
     */
    @XmlElement(name = "length")
    private int length = 0;
    /**
     * La largeur de la salle
     */
    @XmlElement(name = "width")
    private int width = 0;
    /**
     * L'épaisseur des murs
     */
    @XmlElement(name = "mur-weight")
    private int weight = 0;
    /**
     * La largeur du separateur
     */
    @XmlElement(name = "separator-width")
    private int separator = 0;
    /**
     * La liste des cotés d'une salle
     */
    @XmlElementWrapper(name = "cotes")
    @XmlElement(name = "cote")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Cote> cotes = new ArrayList<>();

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public List<Cote> getCotes() {
        return cotes;
    }

    public void setCotes(List<Cote> cotes) {
        this.cotes = cotes;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSeparator() {
        return separator;
    }

    public void setSeparator(int separator) {
        this.separator = separator;
    }

    @JsonIgnore
    public JTable properties(final IDrawing.Listerner listener) {
        JTable tableSalle = new javax.swing.JTable();
        try {
            final DefaultTableModel modelSalle = new javax.swing.table.DefaultTableModel(
                    new Object[][]{
                            {"Location", "[" + getOrigin().x + "," + getOrigin().y + "]"},
                            {"Dimension", "[" + getLength() + "," + getWidth() + "]"},
                            {"Hauteur des murs", getHeight()},
                            {"Epaisseur des murs", getWeight()},
                            {"Séparateur", getSeparator()},},
                    new String[]{"", ""}
            ) {
                boolean[] canEdit = new boolean[]{false, true};

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            tableSalle.setModel(modelSalle);
            modelSalle.addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent event) {
                    try {
                        if (event.getType() == TableModelEvent.UPDATE) {
                            Object value = modelSalle.getValueAt(event.getFirstRow(), event.getColumn());
                            if (value != null ? value.toString().trim().isEmpty() : true) {
                                return;
                            }
                            switch (event.getFirstRow()) {
                                case 0: {//Location
                                    String[] locations = value.toString().replace("[", "").replace("]", "").trim().split(",");
                                    int x = Integer.valueOf(locations[0].trim());
                                    int y = Integer.valueOf(locations[1].trim());
                                    setOrigin(new Point(x, y));
                                    break;
                                }
                                case 1: {//Dimension
                                    String[] dimensions = value.toString().replace("[", "").replace("]", "").trim().split(",");
                                    int length = Integer.valueOf(dimensions[0].trim());
                                    int width = Integer.valueOf(dimensions[1].trim());
                                    setLength(length);
                                    setWidth(width);
                                    break;
                                }
                                case 2: {//Height
                                    setHeight(Integer.valueOf(value.toString()));
                                    break;
                                }
                                case 3: {//Weight
                                    setWeight(Integer.valueOf(value.toString()));
                                    break;
                                }
                                case 4: {//Separator
                                    setSeparator(Integer.valueOf(value.toString()));
                                    break;
                                }
                            }
                            if (listener != null) {
                                listener.refresh();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(Salle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Salle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tableSalle;
    }

    @JsonIgnore
    public Salle build() {
        try {
            int x = getOrigin().x;
            int y = getOrigin().y;
            Cote cote1 = new Cote(getCotes().size() + 1, this, Cote.Position.NORTH);
            cote1.setA(new Point(x, y));
            Mur mur1 = new Mur(cote1.getElements().size() + 1, cote1, getLength());
            mur1.setA(new Point(x, y));
            mur1.setFirst(true);
            mur1.setLast(true);
            cote1.getElements().add(mur1.build());
            getCotes().add(cote1.build());

            x += getLength() - getWeight();
            Cote cote2 = new Cote(getCotes().size() + 1, this, Cote.Position.EAST);
            cote2.setA(new Point(x, y));
            Mur mur2 = new Mur(cote2.getElements().size() + 1, cote2, getWidth());
            mur2.setA(new Point(x, y));
            mur2.setFirst(true);
            mur2.setLast(true);
            cote2.getElements().add(mur2.build());
            getCotes().add(cote2.build());

            x += getWeight() - getLength();
            y += getWidth() - getWeight();
            Cote cote3 = new Cote(getCotes().size() + 1, this, Cote.Position.SOUTH);
            cote3.setA(new Point(x, y));
            Mur mur3 = new Mur(cote3.getElements().size() + 1, cote3, getLength());
            mur3.setA(new Point(x, y));
            mur3.setFirst(true);
            mur3.setLast(true);
            cote3.getElements().add(mur3.build());
            getCotes().add(cote3.build());

            y += getWeight() - getWidth();
            Cote cote4 = new Cote(getCotes().size() + 1, this, Cote.Position.WEST);
            cote4.setA(new Point(x, y));
            Mur mur4 = new Mur(cote4.getElements().size() + 1, cote4, getWidth());
            mur4.setA(new Point(x, y));
            mur4.setFirst(true);
            mur4.setLast(true);
            cote4.getElements().add(mur4.build());
            getCotes().add(cote4.build());
        } catch (Exception ex) {
            Logger.getLogger(Salle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    @JsonIgnore
    public Salle clone() {
        Salle result = new Salle();
        Utils.clone(result, this);
        result.setCotes(new ArrayList<>(getCotes()));
        return result;
    }
}
