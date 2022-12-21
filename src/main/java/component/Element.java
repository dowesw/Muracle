/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component;


import com.fasterxml.jackson.annotation.JsonIgnore;
import tools.Point;

import javax.swing.JTable;
import javax.xml.bind.annotation.*;

import component.drawing.IDrawing;
import tools.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dowes
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Element implements MouseListener {

    private long id;

    private String name;

    /**
     * Les différents point d'angle d'un élément
     */
    @XmlElement(name = "point-a")
    private Point a;
    @XmlElement(name = "point-b")
    private Point b;
    @XmlElement(name = "point-c")
    private Point c;
    @XmlElement(name = "point-d")
    private Point d;
    /**
     * L'indicateur qui précise si l'élément est selectionné
     */
    @XmlTransient
    private boolean selected = false;

    public Element() {

    }

    public Element(long id) {
        this.id = id;
    }

    public Element(long id, String name) {
        this(id);
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Point getC() {
        return c;
    }

    public void setC(Point c) {
        this.c = c;
    }

    public Point getD() {
        return d;
    }

    public void setD(Point d) {
        this.d = d;
    }

    public String getClassName() {
        return getClass().getName();
    }

    @JsonIgnore
    public int[] getXPoints() {
        return new int[]{(getA() != null ? getA().x : 0), (getB() != null ? getB().x : 0), (getC() != null ? getC().x : 0), (getD() != null ? getD().x : 0)};
    }

    @JsonIgnore
    public int[] getYPoints() {
        return new int[]{(getA() != null ? getA().y : 0), (getB() != null ? getB().y : 0), (getC() != null ? getC().y : 0), (getD() != null ? getD().y : 0)};
    }

    @JsonIgnore
    public abstract JTable properties(final IDrawing.Listerner listener);


    @Override
    public void mouseClicked(MouseEvent e) {
        System.err.println(getClassName() + " Clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.err.println(getClassName() + " Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.err.println(getClassName() + " Released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.err.println(getClassName() + " Entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.err.println(getClassName() + " Exited");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return id == element.id && Objects.equals(name, element.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Element clone() {
        try {
            return (Element) Utils.clone(this);
        } catch (Exception ex) {
            Logger.getLogger(Element.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
