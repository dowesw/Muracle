/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package component.drawing;

import domain.ActionManager;

/**
 * @author LYMYTZ
 */
public abstract class ADrawing {

    IDrawing.Listerner listerner;

    ActionManager actionManager;

    public ADrawing(Object objet) {
        this.actionManager = new ActionManager(objet);
    }

    public void undo() {
        this.actionManager.undo();
        setObjet(this.actionManager.getObjet());
    }

    public void redo() {
        this.actionManager.redo();
        setObjet(this.actionManager.getObjet());
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    private int getWidth() {
        return 0;
    }

    private int getHeigth() {
        return 0;
    }

    public abstract void setListerner(IDrawing.Listerner listerner);

    public abstract void setObjet(Object objet);

    public IDrawing.Listerner getListerner() {
        return listerner;
    }


}
