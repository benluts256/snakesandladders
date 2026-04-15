/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author LUTS
 */
package com.mycompany.snakesladders.model;

import java.awt.Color;
import java.util.*;

public class GameState {
    public enum Phase { SETUP, PLAYING, FINISHED }

    private final List<Player> players = new ArrayList<>();
    private final Board board;
    private final Dice  dice;

    private Phase  phase      = Phase.SETUP;
    private int    currentIdx = 0;
    private Player winner     = null;

    private static final Color[] PLAYER_COLORS = {
        new Color(0xE74C3C), new Color(0x2ECC71),
        new Color(0x3498DB), new Color(0xF39C12)
    };

    public GameState() {
        this.board = new Board();
        this.dice  = new Dice();
    }

    public void addPlayer(String name) {
        if (players.size() >= 4) throw new IllegalStateException("Max 4 players");
        players.add(new Player(name, PLAYER_COLORS[players.size()]));
    }

    public void startGame() {
        if (players.size() < 2) throw new IllegalStateException("Need at least 2 players");
        phase = Phase.PLAYING;
    }

    public void resetGame() {
        players.clear();
        phase = Phase.SETUP;
        currentIdx = 0;
        winner = null;
    }

    public TurnResult takeTurn() {
        if (phase != Phase.PLAYING) throw new IllegalStateException("Game not in progress");

        Player player = currentPlayer();
        int roll = dice.roll();
        player.recordRoll();

        int rawPos = player.getPosition() + roll;

        if (rawPos > Board.SIZE) {
            return new TurnResult(player, roll, player.getPosition(), Board.EventType.NONE,
                    player.getPosition(), player.getPosition(), false, "Overshoot – stay put!");
        }

        Board.MoveResult moveResult = board.applyEvent(rawPos);
        int finalPos = moveResult.finalPosition();

        switch (moveResult.eventType()) {
            case SNAKE  -> player.recordSnake();
            case LADDER -> player.recordLadder();
            default -> {}
        }

        player.moveTo(finalPos);

        boolean won = board.isWinningSquare(finalPos);
        if (won) { winner = player; phase = Phase.FINISHED; }
        else     { advanceTurn(); }

        String message = buildMessage(player, roll, rawPos, moveResult);
        return new TurnResult(player, roll, rawPos, moveResult.eventType(),
                moveResult.from(), moveResult.to(), won, message);
    }

    private void advanceTurn() {
        currentIdx = (currentIdx + 1) % players.size();
    }

    private String buildMessage(Player p, int roll, int raw, Board.MoveResult mr) {
        return switch (mr.eventType()) {
            case SNAKE  -> p.getName() + " rolled " + roll + " → " + raw + ". Snake! Slides to " + mr.finalPosition();
            case LADDER -> p.getName() + " rolled " + roll + " → " + raw + ". Ladder! Climbs to " + mr.finalPosition();
            default     -> p.getName() + " rolled " + roll + " → moved to " + mr.finalPosition();
        };
    }

    public Player           currentPlayer()  { return players.get(currentIdx); }
    public List<Player>     getPlayers()     { return Collections.unmodifiableList(players); }
    public Board            getBoard()       { return board;      }
    public Phase            getPhase()       { return phase;      }
    public Player           getWinner()      { return winner;     }
    public int              getCurrentIdx()  { return currentIdx; }

    public record TurnResult(
        Player player, int roll, int rawPosition,
        Board.EventType eventType, int eventFrom, int eventTo,
        boolean won, String message) {}
}
