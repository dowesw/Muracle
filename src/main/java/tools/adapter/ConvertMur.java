package tools.adapter;

import com.fasterxml.jackson.databind.util.StdConverter;
import component.Salle;
import component.element.Mur;

public class ConvertMur extends StdConverter<Mur, Mur> {
    @Override
    public Mur convert(Mur value) {
        // TODO Auto-generated method stub
        if (value != null) {
            Mur result = value.clone();
            result.getAccessoires().clear();
            return result;
        }
        return value;
    }
}
