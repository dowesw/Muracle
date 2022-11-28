/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author LYMYTZ
 */
public class JBloc extends JPanel {

    int heigthContainer = 71;
    JPanel panel_title = new JPanel();
    JLabel lab_title = new JLabel();
    JLabel lab_status = new JLabel();
    JScrollPane jScrollPane1 = new javax.swing.JScrollPane();

    public JBloc(int width, int height, String title) {
        this(width, height, 71, title);
    }

    public JBloc(int width, int height, int heigthContainer, String title) {
        this(width, height, heigthContainer);
        lab_title.setText(title);
    }

    public JBloc(int width, int height, int heigthContainer) {
        super();
        this.setPreferredSize(new Dimension(width, height));
        this.heigthContainer = heigthContainer;
        initComponent();
        lab_status.setText("-");
    }

    public JBloc(int width, int height) {
        this(width, height, 71);
    }

    private void initComponent() {
        panel_title.setPreferredSize(new Dimension(getPreferredSize().width, 20));
        panel_title.setBackground(new java.awt.Color(204, 204, 204));
        lab_status.setText("-");

        panel_title.add(lab_status);

        this.add(panel_title);

        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_salleLayout = new javax.swing.GroupLayout(this);
        this.setLayout(panel_salleLayout);
        panel_salleLayout.setHorizontalGroup(
                panel_salleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_title, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panel_salleLayout.setVerticalGroup(
                panel_salleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_salleLayout.createSequentialGroup()
                        .addComponent(panel_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, heigthContainer, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panel_salle_titleLayout = new javax.swing.GroupLayout(panel_title);
        panel_title.setLayout(panel_salle_titleLayout);
        panel_salle_titleLayout.setHorizontalGroup(
                panel_salle_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_salle_titleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lab_status, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lab_title)
                        .addGap(0, 199, Short.MAX_VALUE))
        );
        panel_salle_titleLayout.setVerticalGroup(
                panel_salle_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_salle_titleLayout.createSequentialGroup()
                        .addGroup(panel_salle_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lab_status)
                                .addComponent(lab_title))
                        .addGap(0, 6, Short.MAX_VALUE))
        );
    }

    public void setTitle(String title) {
        lab_title.setText(title);
    }

    public JPanel getTitle() {
        return panel_title;
    }

    public JLabel getStatus() {
        return lab_status;
    }

    public JPanel getContainer() {
        return this;
    }

    public void setContainer(JComponent container) {
        jScrollPane1.setViewportView(container);
        repaint();
    }

}
