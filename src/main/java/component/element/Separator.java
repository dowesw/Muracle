/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import component.Element;
import tools.Point;

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
@XmlRootElement(name = "separator")
@XmlAccessorType(XmlAccessType.FIELD)
public class Separator extends Element {

    /**
     * Le coté dont appartient le séparateur
     */
    @XmlTransient
    private Cote cote;

    /**
     * Le mur de gauche rattaché au séparateur
     */
    @XmlElement(name = "mur-gauche")
    private Mur murGauche;

    /**
     * Le mur de droit rattaché au séparateur
     */
    @XmlElement(name = "mur-droite")
    private Mur murDroite;

    public Separator(long id, Cote cote) {
        this(id, "Séparateur N°" + id, cote);
    }

    public Separator(long id, String name, Cote cote) {
        super(id, name);
        this.cote = cote;
    }

    public Separator(long id, Cote cote, Mur murGauche, Mur murDroite) {
        this(id, "Séparateur N°" + id, cote, murGauche, murDroite);

    }

    public Separator(long id, String name, Cote cote, Mur murGauche, Mur murDroite) {
        this(id, name, cote);
        this.murDroite = murDroite;
        this.murGauche = murGauche;
    }

    @JsonIgnore
    public Cote getCote() {
        return cote;
    }

    public void setCote(Cote cote) {
        this.cote = cote;
    }

    public Mur getMurGauche() {
        return murGauche;
    }

    public void setMurGauche(Mur murGauche) {
        this.murGauche = murGauche;
    }

    public Mur getMurDroite() {
        return murDroite;
    }

    public void setMurDroite(Mur murDroite) {
        this.murDroite = murDroite;
    }

    @JsonIgnore
    @Override
    public JTable properties(final IDrawing.Listerner listener) {
        JTable table = new javax.swing.JTable();
        final Separator current = this;
        try {
            final DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                    new Object[][]{
                            {"Name", getName()},
                            {"Location", "[" + getA().x + "," + getB().y + "]"},
                            {"Epaisseur", getCote().getSalle().getSeparator()}},
                    new String[]{"", ""}
            ) {
                boolean[][] canEdit = new boolean[][]{new boolean[]{false, false}, new boolean[]{false, true}, new boolean[]{false, false}};

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
                                case 0: {//Name
                                    System.out.println("Name : " + value);
                                    break;
                                }
                                case 1: {//Location
                                    String[] locations = value.toString().replace("[", "").replace("]", "").trim().split(",");
                                    int x = Integer.valueOf(locations[0].trim());
                                    int y = Integer.valueOf(locations[1].trim());
                                    Point point = new Point(x, y);
                                    Utils.updateMursWhenUpdateSeparator(current, point, getCote().getSalle());
                                    break;
                                }
                                case 2: {//Epaisseur
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
    public Separator build() {
        try {
            int width = getCote().getSalle().getSeparator();
            int height = getCote().getSalle().getHeight();
            setB(new Point(getA().x + width, getA().y));
            setC(new Point(getB().x, getB().y + height));
            setD(new Point(getA().x, getC().y));
        } catch (Exception ex) {
            Logger.getLogger(Separator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}
