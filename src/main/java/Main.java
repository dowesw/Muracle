/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import component.Element;
import tools.Config;
import tools.Constantes;
import tools.adapter.DeserializeElement;
import view.FrameMain;

/**
 * @author dowes
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Class<?> c = Main.class;
        Constantes.LOADER = c.getClassLoader();
        Constantes.PATH = c.getResource(c.getSimpleName() + ".class").getPath().replace(c.getSimpleName() + ".class", "");
        Constantes.PATH = Constantes.PATH.substring(1);

        Config.load();

        FrameMain frame = new FrameMain();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
