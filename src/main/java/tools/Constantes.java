/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import component.Element;
import tools.adapter.DeserializeElement;

/**
 *
 * @author dowes
 */
public class Constantes {

    public static String PATH;
    public static ClassLoader LOADER;
    public final static Gson GSON = new GsonBuilder().registerTypeAdapter(Element.class, new DeserializeElement()).create();

}
