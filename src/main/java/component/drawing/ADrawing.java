/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package component.drawing;

/**
 * @author LYMYTZ
 */
public abstract class ADrawing {

    IDrawing.Listerner listerner;

    private int getWidth(){
        return 0;
    }

    private int getHeigth(){
        return 0;
    }

    public abstract void setListerner(IDrawing.Listerner listerner);

    public IDrawing.Listerner getListerner() {
        return listerner;
    }


}
