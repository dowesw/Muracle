/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.element.accessoire;

import component.element.Accessoire;
import component.element.Mur;

/**
 *
 * @author LYMYTZ
 */
public class Porte extends Accessoire {

    private Porte(){
        super();
    }

    public Porte(long id, String name, Mur mur, int width, int heigth) {
        super(id, name, mur, Type.MIXTE, width, heigth);
    }

    public Porte(long id, Mur mur, int width, int heigth) {
        this(id, "Porte N°" + id, mur, width, heigth);
    }

}
