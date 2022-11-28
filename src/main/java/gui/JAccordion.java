/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author dowes
 */
public class JAccordion {

    int current = 0;
    JPanel container = new JPanel();
    Panel[] panels = new Panel[]{};

    public JAccordion(JPanel container, Panel[] panels, int current) {
        this.container = container;
        this.panels = panels;
        this.current = current;
    }

    public JAccordion(JPanel container, Panel[] panels) {
        this(container, panels, 0);
    }

    public void build() {
        if (panels != null) {
            for (int i = 0; i < panels.length; i++) {
                if (current != i) {
                    panels[i].slideUp();
                }
            }
        }
    }

    public static class Panel {

        JLabel status = new JLabel();
        JPanel title = new JPanel();
        JPanel container = new JPanel();

        int height = 0;
        boolean open = true;

        public Panel(JBloc bloc) {
            this(bloc.getStatus(), bloc.getTitle(), bloc.getContainer());
        }

        public Panel(JLabel status, JPanel title, JPanel container) {
            this.status = status;
            this.title = title;
            this.container = container;

            initComponent();
        }

        private void initComponent() {
            if (container != null) {
                height = container.getPreferredSize().height;
            }
            if (title != null) {
                title.addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (open) {
                            slideUp();
                        } else {
                            slideDown();
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
            }
        }

        private void slideUp() {
            if (container != null && title != null) {
                SwingUtilities.invokeLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                int width = container.getPreferredSize().width;
                                int height = title.getPreferredSize().height;
                                container.setPreferredSize(new Dimension(width,height));
                                container.repaint();
                            }
                        }
                );
            }
            if (status != null) {
                status.setText("+");
            }
            open = false;
        }

        private void slideDown() {
            if (container != null) {
                SwingUtilities.invokeLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                container.setPreferredSize(new Dimension(container.getPreferredSize().width, height));
                                container.repaint();
                            }
                        }
                );
            }
            if (status != null) {
                status.setText("-");
            }
            open = true;
        }

    }
}
