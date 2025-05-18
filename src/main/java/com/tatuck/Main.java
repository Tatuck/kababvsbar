package com.tatuck;

import javax.swing.JFrame;

import com.tatuck.view.ChangePanels;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("Malasaña simulator");
        
        ChangePanels changePanels = ChangePanels.getInstance();
        changePanels.addComponentToPane(frame.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
