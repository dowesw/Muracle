package tools.adapter;

import com.google.gson.*;
import component.Element;
import component.element.Accessoire;
import component.element.accessoire.Porte;
import tools.Utils;

import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeserializeElement implements JsonDeserializer<Element> {

    @Override
    public Element deserialize(JsonElement node, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (node.isJsonNull()) {
            return null;
        }
        JsonElement value = node.getAsJsonObject().get("className");
        if (value.isJsonNull()) {
            return null;
        }
        String className = value != null ? !value.isJsonNull() ? value.getAsString() : null : null;
        if (!Utils.asString(className)) {
            return context.deserialize(node, Element.class);
        }
        Element result = null;
        try {
            if (className.equals("component.element.Accessoire")) {
                return null;
            }
            Class<?> eclass = Class.forName(className);
            result = context.deserialize(node, eclass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeserializeElement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
