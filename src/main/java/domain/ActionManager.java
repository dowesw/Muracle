package domain;

import tools.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionManager {

    private List<String> states;
    private int index = -1;
    private Object objet;

    public ActionManager(Object objet) {
        states = new ArrayList<>();
        this.objet = objet;
        action();
    }

    public void setObjet(Object objet) {
        this.objet = objet;
    }

    public Object getObjet(){
        return objet;
    }

    private Object get() {
        try {
            if (this.index > -1 && this.index < this.states.size()) {
                return Utils.getObjectFromString(this.states.get(this.index), this.objet.getClass());
            }
        } catch (Exception ex) {
            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void action() {
        try {
            this.states.add(Utils.getStringFromObject(this.objet));
            this.index++;
        } catch (Exception ex) {
            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void undo() {
        try {
            if (displayUndo()) {
                this.index--;
                Object objet = get();
                if (objet != null) {
                    setObjet(objet);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redo() {
        try {
            if (displayRedo()) {
                this.index++;
                Object objet = get();
                if (objet != null) {
                    setObjet(objet);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean displayUndo() {
        return this.index > 0;
    }

    public boolean displayRedo() {
        return this.index < this.states.size()-1;
    }

}
