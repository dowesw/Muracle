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
public class TrouAir extends Accessoire {

    private RetourAir retourAir;

    public TrouAir(long id, String name, Mur mur, RetourAir retourAir, int width, int heigth) {
        super(id, name, mur, Type.EXTERNE, width, heigth);
        this.retourAir = retourAir;
    }

    public TrouAir(long id, Mur mur, RetourAir retourAir, int width, int heigth) {
        this(id, "TrouAir N°" + id, mur, retourAir, width, heigth);
    }

    public RetourAir getRetourAir() {
        return retourAir;
    }

    public void setRetourAir(RetourAir retourAir) {
        this.retourAir = retourAir;
    }

}
