package tools.adapter;

import com.fasterxml.jackson.databind.util.StdConverter;
import component.Salle;

public class ConvertSalle extends StdConverter<Salle, Salle> {
    @Override
    public Salle convert(Salle value) {
        // TODO Auto-generated method stub
        if (value != null) {
            Salle result = value.clone();
            result.getCotes().clear();
            return result;
        }
        return value;
    }
}
