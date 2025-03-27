package com.tatuck.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;

public class StartPanel extends JPanel{
    Font fontButtons = new Font("Arial", Font.BOLD, 40);
    public StartPanel(){
        this.setLayout(new GridBagLayout());

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        JButton button1Player = new JButton("1 JUGADOR");
        this.styleButton(button1Player);

        JButton button2Players = new JButton("2 JUGADORES");
        this.styleButton(button2Players);
        button2Players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ChangePanels cp = ChangePanels.getInstance();
                cp.getGamePanel().reset();
                cp.changePanels("game");
                cp.getGamePanel().requestFocusInWindow();
            }
        });

        container.add(button1Player);
        container.add(Box.createVerticalStrut(10));
        container.add(button2Players);
        this.add(container);
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
