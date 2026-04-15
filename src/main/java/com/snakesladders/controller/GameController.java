/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snakesladders.controller;

import com.snakesladders.model.GameState;
import com.snakesladders.view.MainWindow;
import com.snakesladders.view.SetupDialog;
import javax.swing.*;
import java.util.List;

public class GameController {
    private final MainWindow view;
    private GameState state;
    private int lastRoll = 0;

    public GameController(MainWindow view) {
        this.view  = view;
        this.state = new GameState();
    }

    public void newGame() {
        SetupDialog dialog = view.showSetupDialog();
        if (!dialog.isConfirmed()) return;

        List<String> names = dialog.getValidNames();
        state = new GameState();
        for (String name : names) state.addPlayer(name);
        state.startGame();
        lastRoll = 0;

        view.clearLog();
        view.appendLog("=== New game started with " + names.size() + " players ===");
        view.appendLog(state.currentPlayer().getName() + " goes first.");
        view.updateView(state, lastRoll);
    }

    public void rollDice() {
        if (state.getPhase() != GameState.Phase.PLAYING) return;
        GameState.TurnResult result = state.takeTurn();
        lastRoll = result.roll();
        animateDice(result);
    }

    private void animateDice(GameState.TurnResult result) {
        Timer timer = new Timer(80, null);
        final int[] frame = {0};
        timer.addActionListener(e -> {
            frame[0]++;
            if (frame[0] >= 5) { timer.stop(); applyResult(result); }
            else view.updateView(state, (int)(Math.random() * 6) + 1);
        });
        timer.start();
    }

    private void applyResult(GameState.TurnResult result) {
        view.appendLog(result.message());
        view.updateView(state, lastRoll);
        if (result.won()) {
            Timer delay = new Timer(600, e -> view.showWinDialog(result.player().getName()));
            delay.setRepeats(false);
            delay.start();
        }
    }
}
