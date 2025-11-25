package com.group13.UI;
import javax.swing.*;
import java.awt.*;

public class GenerateReportScreen extends JPanel implements Screen {
    
    public GenerateReportScreen(MainFrame frame) {
        setSize(500, 450);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add components for report generation here
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}