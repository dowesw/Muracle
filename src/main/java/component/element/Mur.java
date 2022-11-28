/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import component.Element;
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
import tools.adapter.ConvertCote;

/**
 * @author dowes
 */
@XmlRootElement(name = "mur")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mur extends Element {

    /**
     * Indication si le mur est le premier mur du coté
     */
    @XmlTransient
    private boolean first = false;
    /**
     * Indication si le mur est le dernier mur du coté
     */
    @XmlTransient
    private boolean last = false;
    /**
     * La largeur d'un mur
     */
    private int width = 0;
    /**
     * Le coté dont appartient le mur
     */
    @XmlTransient
    @JsonSerialize(converter = ConvertCote.class)
    private Cote cote;
    /**
     * La liste des accessoires du mur
     */
    @XmlElementWrapper(name = "accessoires")
    @XmlElement(name = "accessoire")
    private List<Accessoire> accessoires = new ArrayList<>();

    private Mur() {
    }

    public Mur(long id, Cote cote) {
        this(id, "Mûr N°" + id, cote);
    }

    public Mur(long id, String name, Cote cote) {
        super(id, name);
        this.cote = cote;
    }

    public Mur(long id, String name, Cote cote, int width) {
        this(id, name, cote);
        this.width = width;
    }

    public Mur(long id, Cote cote, int width) {
        this(id, "Mûr N°" + id, cote, width);
    }

    public Cote getCote() {
        return cote;
    }

    public void setCote(Cote cote) {
        this.cote = cote;
    }

    /**
     * La liste des accessoires interne du mur
     *
     * @return
     */
    @XmlTransient
    @JsonIgnore
    public List<Accessoire> getAccessoiresInterne() {
        List<Accessoire> result = new ArrayList<>();
        for (Accessoire item : accessoires) {
            if (item.getType().equals(Accessoire.Type.MIXTE) || item.getType().equals(Accessoire.Type.INTERNE)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * La liste des accessoires externe du mur
     *
     * @return
     */
    @XmlTransient
    @JsonIgnore
    public List<Accessoire> getAccessoiresExterne() {
        List<Accessoire> result = new ArrayList<>();
        for (Accessoire item : accessoires) {
            if (item.getType().equals(Accessoire.Type.MIXTE) || item.getType().equals(Accessoire.Type.EXTERNE)) {
                result.add(item);
            }
        }
        return result;
    }

    public List<Accessoire> getAccessoires() {
        return accessoires;
    }

    public void setAccessoires(List<Accessoire> accessoires) {
        this.accessoires = accessoires;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    @JsonIgnore
    public JTable properties(final IDrawing.Listerner listener) {
        JTable table = new javax.swing.JTable();
        try {
            final DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                    new Object[][]{
                            {"Name", getName()},
                            {"Dimension", "[" + getWidth() + ", " + getCote().getSalle().getHeight() + "]"},
                            {"Point A", "[" + getA().x + ", " + getA().y + "]"},
                            {"Point B", "[" + getB().x + ", " + getB().y + "]"},
                            {"Point C", "[" + getC().x + ", " + getC().y + "]"},
                            {"Point D", "[" + getD().x + ", " + getD().y + "]"},
                            {"Nombre d'accessoire", getAccessoires().size()}},
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
                            }
                            if (listener != null) {
                                listener.refresh();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(Mur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Mur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    @JsonIgnore
    public Mur build() {
        try {
            int height = getCote().getSalle().getHeight();
            setB(new Point(getA().x + width, getA().y));
            setC(new Point(getB().x, getB().y + height));
            setD(new Point(getA().x, getC().y));
        } catch (Exception ex) {
            Logger.getLogger(Mur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    @JsonIgnore
    public Mur clone() {
        Mur result = new Mur();
        Utils.clone(result, this);
        result.setAccessoires(new ArrayList<>(getAccessoires()));
        return result;
    }

}
