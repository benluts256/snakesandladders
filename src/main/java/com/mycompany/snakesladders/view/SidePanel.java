/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakesladders.view;

import com.mycompany.snakesladders.model.GameState;
import com.mycompany.snakesladders.model.Player;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class SidePanel extends JPanel {
    private static final String[] DICE_FACES = {"", "\u2680", "\u2681", "\u2682", "\u2683", "\u2684", "\u2685"};

    private final JLabel    diceLabel    = new JLabel("\u2680", SwingConstants.CENTER);
    private final JButton   rollButton   = new JButton("Roll Dice");
    private final JPanel    playersPanel = new JPanel();
    private final JTextArea logArea      = new JTextArea(10, 20);
    private final JLabel    turnLabel    = new JLabel("", SwingConstants.CENTER);

    public SidePanel() {
        setLayout(new BorderLayout(0, 12));
        setBackground(new Color(0xFAF3E0));
        setBorder(new EmptyBorder(20, 16, 20, 20));
        setPreferredSize(new Dimension(270, 700));
        add(buildTopSection(),     BorderLayout.NORTH);
        add(buildPlayersSection(), BorderLayout.CENTER);
        add(buildLogSection(),     BorderLayout.SOUTH);
    }

    private JPanel buildTopSection() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel title = new JLabel("Snakes & Ladders", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(new Color(0x5D4037));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(title);
        p.add(Box.createVerticalStrut(4));

        diceLabel.setFont(new Font("Serif", Font.PLAIN, 72));
        diceLabel.setForeground(new Color(0x5D4037));
        diceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(diceLabel);
        p.add(Box.createVerticalStrut(8));

        turnLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        turnLabel.setForeground(new Color(0x5D4037));
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(turnLabel);
        p.add(Box.createVerticalStrut(12));

        rollButton.setBackground(new Color(0x8B5E3C));
        rollButton.setForeground(Color.WHITE);
        rollButton.setFont(new Font("Georgia", Font.BOLD, 14));
        rollButton.setFocusPainted(false);
        rollButton.setBorderPainted(false);
        rollButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rollButton.setMaximumSize(new Dimension(200, 42));
        p.add(rollButton);
        p.add(Box.createVerticalStrut(16));
        return p;
    }

    private JPanel buildPlayersSection() {
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setOpaque(false);
        JLabel heading = new JLabel("Players");
        heading.setFont(new Font("Georgia", Font.BOLD, 13));
        heading.setForeground(new Color(0x9C8A6E));
        playersPanel.add(heading);
        playersPanel.add(Box.createVerticalStrut(6));
        return playersPanel;
    }

    private JPanel buildLogSection() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setOpaque(false);
        JLabel heading = new JLabel("Game Log");
        heading.setFont(new Font("Georgia", Font.BOLD, 13));
        heading.setForeground(new Color(0x9C8A6E));
        p.add(heading, BorderLayout.NORTH);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        logArea.setBackground(new Color(0xFDF6EC));
        logArea.setForeground(new Color(0x5D4037));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBorder(new EmptyBorder(6, 6, 6, 6));
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xD4C5A9)));
        scroll.setPreferredSize(new Dimension(240, 160));
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    public void updateState(GameState state, int lastRoll) {
        diceLabel.setText(lastRoll > 0 ? DICE_FACES[lastRoll] : "\u2680");
        if (state.getPhase() == GameState.Phase.PLAYING) {
            Player cur = state.currentPlayer();
            turnLabel.setText(cur.getName() + "'s turn");
            turnLabel.setForeground(cur.getColor());
        } else if (state.getPhase() == GameState.Phase.FINISHED) {
            turnLabel.setText("Game Over!");
            turnLabel.setForeground(new Color(0x27AE60));
        }
        rollButton.setEnabled(state.getPhase() == GameState.Phase.PLAYING);
        refreshPlayers(state.getPlayers(), state.getCurrentIdx(), state.getPhase());
    }

    private void refreshPlayers(List<Player> players, int currentIdx, GameState.Phase phase) {
        while (playersPanel.getComponentCount() > 2) playersPanel.remove(playersPanel.getComponentCount()-1);
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            boolean isCurrent = (i == currentIdx) && (phase == GameState.Phase.PLAYING);
            JPanel row = new JPanel(new BorderLayout(8, 0));
            row.setOpaque(isCurrent);
            if (isCurrent) {
                row.setBackground(new Color(p.getColor().getRed(), p.getColor().getGreen(), p.getColor().getBlue(), 30));
                row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(p.getColor(), 1), new EmptyBorder(4,6,4,6)));
            } else {
                row.setBorder(new EmptyBorder(4,6,4,6));
            }
            JLabel info = new JLabel(p.getName() + "  ->  sq " + p.getPosition());
            info.setFont(new Font("Georgia", isCurrent ? Font.BOLD : Font.PLAIN, 12));
            info.setForeground(isCurrent ? p.getColor().darker() : new Color(0x5D4037));
            row.add(info, BorderLayout.CENTER);
            JLabel stats = new JLabel("S:" + p.getSnakeCount() + " L:" + p.getLadderCount());
            stats.setFont(new Font("Monospaced", Font.PLAIN, 11));
            stats.setForeground(new Color(0x9C8A6E));
            row.add(stats, BorderLayout.EAST);
            playersPanel.add(row);
            playersPanel.add(Box.createVerticalStrut(4));
        }
        playersPanel.revalidate();
        playersPanel.repaint();
    }

    public void appendLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public void clearLog()              { logArea.setText(""); }
    public JButton getRollButton()      { return rollButton;   }
}
