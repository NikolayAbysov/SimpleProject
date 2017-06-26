package com.abysov;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;


class StatusFrame extends javax.swing.JFrame {

    JLabel jLabel;
    JScrollPane sp_jTextArea;

    protected StatusFrame() {

        setTitle("Move status");

        JFrameLayout customLayout = new JFrameLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);

        jLabel = new JLabel("Current progress:");
        getContentPane().add(jLabel);

        //Консольный вывод
        JConsole jConsole = new JConsole();

        sp_jTextArea = new JScrollPane(jConsole);
        getContentPane().add(sp_jTextArea);

        //Автоскролл для JScrollPane
        DefaultCaret caret = (DefaultCaret) jConsole.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        sp_jTextArea.setViewportView(jConsole);

        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    protected static void main(String args[]) {
        javax.swing.JFrame window = new javax.swing.JFrame();

        window.pack();
        window.show();
    }
}

class JFrameLayout implements LayoutManager {

    protected JFrameLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 410 + insets.left + insets.right;
        dim.height = 500 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+8,150,24);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+40,388,410);}
    }
}
