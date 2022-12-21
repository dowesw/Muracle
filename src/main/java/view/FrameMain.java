/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.*;

import domain.Controller;
import gui.DrawingPanel;
import tools.Constantes;
import tools.Messages;

/**
 * @author dowes
 */
public class FrameMain extends javax.swing.JFrame {

    private final Controller controller;

    /**
     * Creates new form Frame_Main
     */
    public FrameMain() {
        initComponents();
        controller = new Controller();
        Constantes.MAIN = this;
    }

    public Controller getControler() {
        return controller;
    }

    public JPanel getTools() {
        return panel_tools;
    }

    public DrawingPanel getDisplay() {
        return (DrawingPanel) panel_display;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_main = new javax.swing.JSplitPane();
        panel_tools = new javax.swing.JPanel();
        panel_display = new DrawingPanel(this);
        scroll_display = new javax.swing.JScrollPane(panel_display);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        item_new = new javax.swing.JMenuItem();
        item_open = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        item_save = new javax.swing.JMenuItem();
        item_save_as = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        item_exit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        item_undo = new javax.swing.JMenuItem();
        item_redo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        item_cut = new javax.swing.JMenuItem();
        item_copy = new javax.swing.JMenuItem();
        item_paste = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        item_show_regle = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        item_zoom_in = new javax.swing.JMenuItem();
        item_zoom_out = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Muracle");

        panel_main.setDividerLocation(250);

        javax.swing.GroupLayout panel_toolsLayout = new javax.swing.GroupLayout(panel_tools);
        panel_tools.setLayout(panel_toolsLayout);
        panel_toolsLayout.setHorizontalGroup(
                panel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 249, Short.MAX_VALUE)
        );
        panel_toolsLayout.setVerticalGroup(
                panel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 449, Short.MAX_VALUE)
        );

        panel_main.setLeftComponent(panel_tools);

        javax.swing.GroupLayout panel_displayLayout = new javax.swing.GroupLayout(panel_display);
        panel_display.setLayout(panel_displayLayout);
        panel_displayLayout.setHorizontalGroup(
                panel_displayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 641, Short.MAX_VALUE)
        );
        panel_displayLayout.setVerticalGroup(
                panel_displayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 449, Short.MAX_VALUE)
        );
        scroll_display.setLayout(new ScrollPaneLayout());
        scroll_display.setViewportView(panel_display);
        panel_main.setRightComponent(scroll_display);

        jMenu1.setText("File");

        item_new.setText("New");
        item_new.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        item_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_newActionPerformed(evt);
            }
        });
        jMenu1.add(item_new);

        item_open.setText("Open");
        item_open.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        item_open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_openActionPerformed(evt);
            }
        });
        jMenu1.add(item_open);
        jMenu1.add(jSeparator1);

        item_save.setText("Save");
        item_save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        item_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_saveActionPerformed(evt);
            }
        });
        jMenu1.add(item_save);

        item_save_as.setText("Save As");
        item_save_as.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        item_save_as.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_save_asActionPerformed(evt);
            }
        });
        jMenu1.add(item_save_as);
        jMenu1.add(jSeparator2);

        item_exit.setText("Exit");
        item_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        item_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_exitActionPerformed(evt);
            }
        });
        jMenu1.add(item_exit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        item_undo.setText("Undo");
        item_undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        item_undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_undo_asActionPerformed(evt);
            }
        });
        jMenu2.add(item_undo);

        item_redo.setText("Redo");
        item_redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        item_redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_redo_asActionPerformed(evt);
            }
        });
        jMenu2.add(item_redo);
        jMenu2.add(jSeparator3);

        item_cut.setText("Cut");
        item_cut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenu2.add(item_cut);

        item_copy.setText("Copy");
        item_copy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenu2.add(item_copy);

        item_paste.setText("Paste");
        item_paste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenu2.add(item_paste);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("View");

        item_show_regle.setText("Show Grille");
        item_show_regle.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        item_show_regle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_show_regleActionPerformed(evt);
            }
        });
        jMenu3.add(item_show_regle);
        jMenu3.add(jSeparator4);

        item_zoom_in.setText("Zoom In");
        jMenu3.add(item_zoom_in);

        item_zoom_out.setText("Zoom Out");
        jMenu3.add(item_zoom_out);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel_main)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel_main, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void item_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_newActionPerformed
        // TODO add your handling code here:
        if (controller.getSalle() != null) {
            int result = Messages.confirm(this, "Confirmation", "Etes-vous sûr de fermer cette salle");
            if (result == JOptionPane.NO_OPTION) {
                return;
            }
        }
        controller.newProjet();
        ((DrawingPanel) panel_display).drawSalle(controller.getSalle());
    }//GEN-LAST:event_item_newActionPerformed

    private void item_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_openActionPerformed
        // TODO add your handling code here:
        controller.setSalle(controller.onRestoreBackup(this));
        if (controller.getSalle() != null) {
            ((DrawingPanel) panel_display).drawSalle(controller.getSalle());
        }

    }//GEN-LAST:event_item_openActionPerformed

    private void item_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_exitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_item_exitActionPerformed

    private void item_show_regleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:item_show_regleActionPerformed
        // TODO add your handling code here:
        controller.setDisplayGrille(!controller.isDisplayGrille());
        item_show_regle.setText(controller.isDisplayGrille() ? "Hide Grille" : "Show Grille");
        panel_display.repaint();
    }//GEN-LAST:item_show_regleActionPerformed

    private void item_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_saveActionPerformed
        // TODO add your handling code here:
        if (controller.getSalle() != null) {
            controller.onGenerateBackup(this, controller.getSalle(), false);
        }
    }//GEN-LAST:event_item_saveActionPerformed

    private void item_save_asActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_save_asActionPerformed
        // TODO add your handling code here:
        if (controller.getSalle() != null) {
            controller.onGenerateBackup(this, controller.getSalle(), true);
        }
    }//GEN-LAST:event_item_save_asActionPerformed

    private void item_undo_asActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:item_undo_asActionPerformed
        // TODO add your handling code here:
        ((DrawingPanel) panel_display).undo();
    }//GEN-LAST:item_undo_asActionPerformed

    private void item_redo_asActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:item_redo_asActionPerformed
        // TODO add your handling code here:
        ((DrawingPanel) panel_display).redo();
    }//GEN-LAST:item_redo_asActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem item_copy;
    private javax.swing.JMenuItem item_cut;
    private javax.swing.JMenuItem item_exit;
    private javax.swing.JMenuItem item_new;
    private javax.swing.JMenuItem item_open;
    private javax.swing.JMenuItem item_paste;
    private javax.swing.JMenuItem item_redo;
    private javax.swing.JMenuItem item_save;
    private javax.swing.JMenuItem item_save_as;
    private javax.swing.JMenuItem item_show_regle;
    private javax.swing.JMenuItem item_undo;
    private javax.swing.JMenuItem item_zoom_in;
    private javax.swing.JMenuItem item_zoom_out;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPanel panel_display;
    private javax.swing.JScrollPane scroll_display;
    private javax.swing.JSplitPane panel_main;
    private javax.swing.JPanel panel_tools;
    // End of variables declaration//GEN-END:variables
}
