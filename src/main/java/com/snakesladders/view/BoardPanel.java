/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LUTS
 */
package com.snakesladders.view;

import com.snakesladders.model.Board;
import com.snakesladders.model.GameState;
import com.snakesladders.model.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import java.util.Map;

public class BoardPanel extends JPanel {
    private static final int CELL = 64;
    private static final int PAD  = 20;
    private static final int BOARD_PX = CELL * 10;

    private static final Color CELL_LIGHT  = new Color(0xFDF6EC);
    private static final Color CELL_DARK   = new Color(0xF0E8D8);
    private static final Color GRID_LINE   = new Color(0xD4C5A9);
    private static final Color NUM_COLOR   = new Color(0x9C8A6E);
    private static final Color SNAKE_COLOR  = new Color(0xC0, 0x39, 0x2B, 180);
    private static final Color LADDER_COLOR = new Color(0x27, 0xAE, 0x60, 180);

    private GameState state;

    public BoardPanel() {
        setPreferredSize(new Dimension(BOARD_PX + PAD * 2, BOARD_PX + PAD * 2));
        setBackground(new Color(0xFAF3E0));
    }

    public void setGameState(GameState state) { this.state = state; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.translate(PAD, PAD);
        drawCells(g2);
        if (state != null) {
            drawSnakesAndLadders(g2, state.getBoard());
            drawPlayers(g2, state.getPlayers());
        }
        drawBorder(g2);
    }

    private void drawCells(Graphics2D g2) {
        g2.setFont(new Font("Georgia", Font.PLAIN, 11));
        for (int sq = 1; sq <= Board.SIZE; sq++) {
            int[] rc = squareToScreen(sq);
            int px = rc[1] * CELL;
            int py = (9 - rc[0]) * CELL;
            g2.setColor((rc[0] + rc[1]) % 2 == 0 ? CELL_LIGHT : CELL_DARK);
            g2.fillRect(px, py, CELL, CELL);
            g2.setColor(GRID_LINE);
            g2.drawRect(px, py, CELL, CELL);
            g2.setColor(NUM_COLOR);
            g2.drawString(String.valueOf(sq), px + 4, py + 14);
        }
    }

    private void drawSnakesAndLadders(Graphics2D g2, Board board) {
        for (Map.Entry<Integer, Integer> e : board.getLadders().entrySet()) drawLadder(g2, e.getKey(), e.getValue());
        for (Map.Entry<Integer, Integer> e : board.getSnakes().entrySet())  drawSnake(g2,  e.getKey(), e.getValue());
    }

    private void drawLadder(Graphics2D g2, int bottom, int top) {
        Point b = centerOf(bottom), t = centerOf(top);
        g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(LADDER_COLOR);
        double angle = Math.atan2(t.y - b.y, t.x - b.x);
        double perp  = angle + Math.PI / 2;
        int dx = (int)(Math.cos(perp) * 6), dy = (int)(Math.sin(perp) * 6);
        g2.drawLine(b.x+dx, b.y+dy, t.x+dx, t.y+dy);
        g2.drawLine(b.x-dx, b.y-dy, t.x-dx, t.y-dy);
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int rungs = (int)(b.distance(t) / 22);
        for (int i = 1; i <= rungs; i++) {
            double r = (double)i/(rungs+1);
            int rx = (int)(b.x + r*(t.x-b.x)), ry = (int)(b.y + r*(t.y-b.y));
            g2.drawLine(rx+dx, ry+dy, rx-dx, ry-dy);
        }
    }

    private void drawSnake(Graphics2D g2, int head, int tail) {
        Point h = centerOf(head), t = centerOf(tail);
        g2.setStroke(new BasicStroke(8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(SNAKE_COLOR);
        int cx1 = h.x+(t.x-h.x)/3+30, cy1 = h.y+(t.y-h.y)/3-40;
        int cx2 = h.x+2*(t.x-h.x)/3-30, cy2 = h.y+2*(t.y-h.y)/3+40;
        g2.draw(new CubicCurve2D.Float(h.x, h.y, cx1, cy1, cx2, cy2, t.x, t.y));
        g2.setColor(new Color(0x96281B)); g2.fillOval(h.x-8, h.y-8, 16, 16);
        g2.setColor(Color.WHITE); g2.setStroke(new BasicStroke(1.5f)); g2.drawOval(h.x-8, h.y-8, 16, 16);
        g2.fillOval(h.x-4, h.y-4, 4, 4); g2.fillOval(h.x+1, h.y-4, 4, 4);
        g2.setColor(Color.BLACK); g2.fillOval(h.x-3, h.y-3, 2, 2); g2.fillOval(h.x+2, h.y-3, 2, 2);
    }

    private void drawPlayers(Graphics2D g2, List<Player> players) {
        java.util.Map<Integer, java.util.List<Player>> byPos = new java.util.LinkedHashMap<>();
        for (Player p : players)
            if (p.getPosition() > 0) byPos.computeIfAbsent(p.getPosition(), k -> new java.util.ArrayList<>()).add(p);
        for (var entry : byPos.entrySet()) {
            List<Player> ps = entry.getValue();
            Point center = centerOf(entry.getKey());
            int n = ps.size();
            for (int i = 0; i < n; i++) {
                int ox = (n > 1) ? -10 + i * (20 / Math.max(n-1,1)) : 0;
                int oy = (n > 1) ? (i%2==0 ? -6 : 6) : 0;
                drawToken(g2, ps.get(i), center.x+ox, center.y+oy);
            }
        }
    }

    private void drawToken(Graphics2D g2, Player p, int x, int y) {
        int r = 12;
        g2.setColor(new Color(0,0,0,50)); g2.fillOval(x-r+2, y-r+2, r*2, r*2);
        g2.setColor(p.getColor());        g2.fillOval(x-r, y-r, r*2, r*2);
        g2.setColor(new Color(255,255,255,100)); g2.fillOval(x-r+3, y-r+3, r-2, r-2);
        g2.setColor(p.getColor().darker()); g2.setStroke(new BasicStroke(1.5f)); g2.drawOval(x-r, y-r, r*2, r*2);
        g2.setColor(Color.WHITE); g2.setFont(new Font("Georgia", Font.BOLD, 11));
        FontMetrics fm = g2.getFontMetrics();
        String init = p.getName().substring(0,1).toUpperCase();
        g2.drawString(init, x - fm.stringWidth(init)/2, y + fm.getAscent()/2 - 1);
    }

    private void drawBorder(Graphics2D g2) {
        g2.setColor(new Color(0xA08060)); g2.setStroke(new BasicStroke(3f));
        g2.drawRect(0, 0, BOARD_PX, BOARD_PX);
    }

    private int[] squareToScreen(int sq) {
        int idx = sq-1, row = idx/Board.COLS, col = idx%Board.COLS;
        if (row%2==1) col = (Board.COLS-1)-col;
        return new int[]{row, col};
    }

    private Point centerOf(int sq) {
        int[] rc = squareToScreen(sq);
        return new Point(rc[1]*CELL + CELL/2, (9-rc[0])*CELL + CELL/2);
    }
}
