/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakesladders.model;

import java.util.Random;

public class Dice {
    private static final int FACES = 6;
    private final Random random = new Random();
    private int lastRoll = 0;

    public int roll()           { lastRoll = random.nextInt(FACES) + 1; return lastRoll; }
    public int getLastRoll()    { return lastRoll; }
}