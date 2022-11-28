package tools.adapter;

import com.fasterxml.jackson.databind.util.StdConverter;
import component.element.Cote;
import component.element.Mur;

public class ConvertCote extends StdConverter<Cote, Cote> {
    @Override
    public Cote convert(Cote value) {
        // TODO Auto-generated method stub
        if (value != null) {
            Cote result = value.clone();
            result.getElements().clear();
            return result;
        }
        return value;
    }
}
