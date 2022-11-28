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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import component.drawing.IDrawing;
import tools.adapter.ConvertMur;

/**
 * @author dowes
 */
@XmlRootElement(name = "accessoire")
@XmlAccessorType(XmlAccessType.FIELD)
public class Accessoire extends Element {

    private Type type;
    private int width;
    private int height;
    @XmlTransient
    @JsonSerialize(converter = ConvertMur.class)
    private Mur mur;

    public Accessoire() {

    }

    public Accessoire(long id, String name, Mur mur, Type type, int width, int height) {
        super(id, name);
        this.mur = mur;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public Accessoire(long id, Mur mur, Type type, int width, int height) {
        this(id, "Accessoire N°" + id, mur, type, width, height);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Mur getMur() {
        return mur;
    }

    public void setMur(Mur mur) {
        this.mur = mur;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @JsonIgnore
    public Accessoire build() {
        try {
            setB(new Point(getA().x + width, getA().y));
            setC(new Point(getB().x, getB().y + height));
            setD(new Point(getA().x, getC().y));
        } catch (Exception ex) {
            Logger.getLogger(Accessoire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    @JsonIgnore
    @Override
    public JTable properties(final IDrawing.Listerner listener) {
        JTable table = new javax.swing.JTable();
        try {
            final DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                    new Object[][]{
                            {"Name", getName()},
                            {"Dimension", "[" + getWidth() + "," + getHeight() + "]"},
                            {"Point A", "[" + getA().x + ", " + getA().y + "]"},
                            {"Point B", "[" + getB().x + ", " + getB().y + "]"},
                            {"Point C", "[" + getC().x + ", " + getC().y + "]"},
                            {"Point D", "[" + getD().x + ", " + getD().y + "]"}},
                    new String[]{"", ""}
            ) {
                boolean[][] canEdit = new boolean[][]{new boolean[]{false, false}, new boolean[]{false, true}, new boolean[]{false, false}, new boolean[]{false, false}, new boolean[]{false, false}, new boolean[]{false, false}, new boolean[]{false, false}};

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[rowIndex][columnIndex];
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
                                case 1: {//Dimension
                                    String[] dimensions = value.toString().replace("[", "").replace("]", "").trim().split(",");
                                    int width = Integer.valueOf(dimensions[0].trim());
                                    int height = Integer.valueOf(dimensions[1].trim());
                                    setWidth(width);
                                    setHeight(height);
                                    break;
                                }
                            }
                            if (listener != null) {
                                listener.refresh();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(Accessoire.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Accessoire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    public enum Type {

        MIXTE, INTERNE, EXTERNE;
    }

}
