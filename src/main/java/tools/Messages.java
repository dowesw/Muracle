/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * @author dowes
 */
public class Messages {

    public static void error(Component frame, String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static int confirm(Component frame, String title, String message) {
        return JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
