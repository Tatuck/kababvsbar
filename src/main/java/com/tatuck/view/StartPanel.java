package com.tatuck.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartPanel extends JPanel{
    public StartPanel(){
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ChangePanels cp = ChangePanels.getInstance();
                cp.getGamePanel().reset();
                cp.changePanels("game");
                cp.getGamePanel().requestFocusInWindow();
            }
        });
        this.add(button);
    }
}
