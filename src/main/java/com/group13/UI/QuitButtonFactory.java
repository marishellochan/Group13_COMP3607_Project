package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.group13.Singelton.GameExit;

public class QuitButtonFactory {
    
    public static JButton createQuitButton(Color btncolour) {
        JButton quitBtn = new JButton("QUIT");
        quitBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        quitBtn.setBackground(Color.WHITE);
        quitBtn.setForeground(Color.RED);
        quitBtn.setPreferredSize(new Dimension(80, 30));
        quitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               GameExit.getInstance().exitGame();
            }
        });
        return quitBtn;
    }

    public static JPanel createQuitButtonPanel(Color btncolour) {
        JPanel quitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        quitPanel.setOpaque(false);
        quitPanel.add(createQuitButton(btncolour));
        return quitPanel;
    }
}
