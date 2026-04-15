/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snakesladders.view;

import com.snakesladders.controller.GameController;
import com.snakesladders.model.GameState;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final BoardPanel boardPanel;
    private final SidePanel  sidePanel;
    private GameController   controller;

    public MainWindow() {
        super("Snakes & Ladders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardPanel = new BoardPanel();
        sidePanel  = new SidePanel();
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(0xFAF3E0));
        root.add(boardPanel, BorderLayout.CENTER);
        root.add(sidePanel,  BorderLayout.EAST);
        setContentPane(root);
        buildMenuBar();
        pack();
        setLocationRelativeTo(null);
    }

    private void buildMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> { if (controller != null) controller.newGame(); });
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(e -> System.exit(0));
        game.add(newGame); game.addSeparator(); game.add(quit);
        bar.add(game);
        setJMenuBar(bar);
    }

    public void setController(GameController controller) {
        this.controller = controller;
        sidePanel.getRollButton().addActionListener(e -> controller.rollDice());
    }

    public void updateView(GameState state, int lastRoll) {
        boardPanel.setGameState(state);
        sidePanel.updateState(state, lastRoll);
    }

    public void appendLog(String msg)  { sidePanel.appendLog(msg); }
    public void clearLog()             { sidePanel.clearLog();     }

    public SetupDialog showSetupDialog() {
        SetupDialog d = new SetupDialog(this);
        d.setVisible(true);
        return d;
    }

    public void showWinDialog(String winnerName) {
        int choice = JOptionPane.showOptionDialog(this,
            winnerName + " wins! Congratulations!", "We have a winner!",
            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, new String[]{"Play Again", "Quit"}, "Play Again");
        if (choice == JOptionPane.YES_OPTION && controller != null) controller.newGame();
        else System.exit(0);
    }
}
