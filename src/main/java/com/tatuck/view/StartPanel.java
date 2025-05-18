package com.tatuck.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

public class StartPanel extends JPanel{
    Font fontButtons = new Font("Arial", Font.BOLD, 40);
    public StartPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(250));

        JLabel text = new JLabel("El simulador de malasaña más real que existe hasta la época!");
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setFont(new Font("Arial", Font.BOLD, 25));
        text.setForeground(Color.BLACK);
        this.add(text);
        JLabel text1 = new JLabel("La verdadera batalla entre los bares y kebabs!");
        text1.setAlignmentX(Component.CENTER_ALIGNMENT);
        text1.setFont(new Font("Arial", Font.BOLD, 25));
        text1.setForeground(Color.BLACK);
        this.add(text1);

        JButton button1Player = new JButton("1 JUGADOR");
        this.styleButton(button1Player);
        button1Player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ChangePanels cp = ChangePanels.getInstance();
                cp.getGamePanel().reset(true);
                cp.changePanels("game");
                cp.getGamePanel().requestFocusInWindow();
            }
        });

        JButton button2Players = new JButton("2 JUGADORES");
        this.styleButton(button2Players);
        button2Players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ChangePanels cp = ChangePanels.getInstance();
                cp.getGamePanel().reset(false);
                cp.changePanels("game");
                cp.getGamePanel().requestFocusInWindow();
            }
        });
        
        

        this.add(Box.createVerticalGlue());

        this.add(button1Player);
        this.add(Box.createVerticalStrut(50));
        this.add(button2Players);

        this.add(Box.createVerticalGlue());

        JLabel text2 = new JLabel("Movimiento - J1: WASD  | J2: FLECHAS");
        text2.setAlignmentX(Component.CENTER_ALIGNMENT);
        text2.setFont(new Font("Arial", Font.BOLD, 15));
        text2.setForeground(Color.ORANGE);
        this.add(text2);
        JLabel text3 = new JLabel("Disparar - J1: ESPACIO  | J2: K");
        text3.setAlignmentX(Component.CENTER_ALIGNMENT);
        text3.setFont(new Font("Arial", Font.BOLD, 15));
        text3.setForeground(Color.ORANGE);
        this.add(text3);
        this.add(Box.createVerticalStrut(10));        
    }

    private void styleButton(JButton button){
        button.setFont(this.fontButtons);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(TextureManager.loadImage("resources/splashScreen.png"), 0, 0, null);
    }
}
