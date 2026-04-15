/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakesladders;

import com.mycompany.snakesladders.controller.GameController;
import com.mycompany.snakesladders.view.MainWindow;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            GameController controller = new GameController(window);
            window.setController(controller);
            window.setVisible(true);
            controller.newGame();
        });
    }
}
