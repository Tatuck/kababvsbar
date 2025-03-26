package com.tatuck.view;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Container;

public class ChangePanels {
    private static JPanel cards;
    private GamePanel gamePanel;
    private StartPanel startPanel;

    private static ChangePanels instance = null;

    public static ChangePanels getInstance(){
        if(instance == null){
            instance = new ChangePanels();
        }
        return instance;
    }

    private ChangePanels(){
        startPanel = new StartPanel();
        gamePanel = new GamePanel();

        cards = new JPanel(new CardLayout());
        cards.add(startPanel, "start");
        cards.add(gamePanel, "game");
    }

    public void addComponentToPane(Container panel){
        panel.add(cards);
    }

    public void changePanels(String namePanel){
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, namePanel);
    }

    public GamePanel getGamePanel(){
        return this.gamePanel;
    }
}
