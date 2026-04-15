/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snakesladders.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SetupDialog extends JDialog {
    private final List<JTextField> nameFields = new ArrayList<>();
    private boolean confirmed = false;

    public SetupDialog(Frame owner) {
        super(owner, "New Game - Player Setup", true);
        buildUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void buildUI() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(0xFAF3E0));
        content.setBorder(new EmptyBorder(24, 32, 24, 32));

        JLabel title = new JLabel("Enter Player Names");
        title.setFont(new Font("Georgia", Font.BOLD, 18));
        title.setForeground(new Color(0x5D4037));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(title);
        content.add(Box.createVerticalStrut(6));

        JLabel sub = new JLabel("2 to 4 players  -  Leave blank to skip");
        sub.setFont(new Font("Georgia", Font.ITALIC, 12));
        sub.setForeground(new Color(0x9C8A6E));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(sub);
        content.add(Box.createVerticalStrut(20));

        String[] defaults = {"Alice", "Bob", "Charlie", "Diana"};
        for (int i = 0; i < 4; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            row.setOpaque(false);
            JLabel lbl = new JLabel("Player " + (i+1) + ":");
            lbl.setFont(new Font("Georgia", Font.PLAIN, 13));
            lbl.setPreferredSize(new Dimension(70, 24));
            JTextField tf = new JTextField(i < 2 ? defaults[i] : "", 14);
            tf.setFont(new Font("Georgia", Font.PLAIN, 13));
            nameFields.add(tf);
            row.add(lbl); row.add(tf);
            content.add(row);
            content.add(Box.createVerticalStrut(8));
        }
        content.add(Box.createVerticalStrut(16));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        btnRow.setOpaque(false);
        JButton startBtn = new JButton("Start Game");
        startBtn.setBackground(new Color(0x8B5E3C));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFont(new Font("Georgia", Font.BOLD, 13));
        startBtn.setFocusPainted(false);
        startBtn.setBorderPainted(false);
        startBtn.addActionListener(e -> {
            if (getValidNames().size() < 2) {
                JOptionPane.showMessageDialog(this, "Please enter at least 2 player names.", "Not enough players", JOptionPane.WARNING_MESSAGE);
                return;
            }
            confirmed = true;
            dispose();
        });
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        btnRow.add(startBtn); btnRow.add(cancelBtn);
        content.add(btnRow);
        setContentPane(content);
        setResizable(false);
    }

    public boolean isConfirmed()       { return confirmed; }
    public List<String> getValidNames() {
        List<String> names = new ArrayList<>();
        for (JTextField tf : nameFields) {
            String t = tf.getText().trim();
            if (!t.isEmpty()) names.add(t);
        }
        return names;
    }
}