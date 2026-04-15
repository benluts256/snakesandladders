/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snakesladders.model;

import java.awt.Color;

public class Player {
    private final String name;
    private final Color  color;
    private int position;
    private int rollCount;
    private int snakeCount;
    private int ladderCount;

    public Player(String name, Color color) {
        this.name  = name;
        this.color = color;
        this.position = 0;
    }

    public void moveTo(int pos)  { this.position = pos; }
    public void recordSnake()    { snakeCount++;  }
    public void recordLadder()   { ladderCount++; }
    public void recordRoll()     { rollCount++;   }

    public String getName()        { return name;        }
    public Color  getColor()       { return color;       }
    public int    getPosition()    { return position;    }
    public int    getRollCount()   { return rollCount;   }
    public int    getSnakeCount()  { return snakeCount;  }
    public int    getLadderCount() { return ladderCount; }
}
