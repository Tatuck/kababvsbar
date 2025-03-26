package com.tatuck;

import javax.swing.JFrame;

import com.tatuck.view.ChangePanels;
import com.tatuck.view.GamePanel;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("Malasaña simulator");
        
        ChangePanels changePanels = ChangePanels.getInstance();
        changePanels.addComponentToPane(frame.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
