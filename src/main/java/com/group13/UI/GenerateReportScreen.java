package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.group13.Singelton.GameController;

public class GenerateReportScreen extends JPanel implements Screen {
    private MainFrame mainFrame;
    private GameController controller;
    private JComboBox<String> formatCombo;
    private JLabel statusLabel;
    
    public GenerateReportScreen(MainFrame frame) {
        this.mainFrame = frame;
        this.controller = GameController.getInstance();
        
        setSize(500, 450);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addComponents();
        updateStatus();
    }

    private void addComponents() {
        // Title
        JLabel titleLabel = new JLabel("Generate Game Report");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setBounds(130, 50, 250, 30);
        add(titleLabel);
        
        // Status label
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBounds(100, 90, 300, 25);
        add(statusLabel);
        
        // Format selection
        JLabel formatLabel = new JLabel("Select Report Format:");
        formatLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        formatLabel.setBounds(150, 140, 200, 25);
        add(formatLabel);
        
        formatCombo = new JComboBox<>(new String[]{"TXT", "DOCX", "PDF"});
        formatCombo.setBounds(150, 170, 200, 30);
        add(formatCombo);
        
        // Generate button
        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        generateBtn.setBounds(150, 230, 200, 40);
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGenerateReport();
            }
        });
        add(generateBtn);
        
        // Exit button
        // JButton exitBtn = new JButton("Exit Application");
        // exitBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        // exitBtn.setBounds(150, 290, 200, 40);
        // exitBtn.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         handleExit();
        //     }
        // });
        // add(exitBtn);
    }
    
    private void updateStatus() {
        if (controller.hasGameData()) {
            int totalTurns = controller.getTotalTurns();
            statusLabel.setText("Game completed with " + totalTurns + " turns recorded");
            statusLabel.setForeground(new Color(0, 128, 0)); // Green
        } else {
            statusLabel.setText("No game data available");
            statusLabel.setForeground(Color.RED);
        }
    }
    
    private void handleGenerateReport() {
        // Check if there's data to generate report from
        if (!controller.hasGameData()) {
            JOptionPane.showMessageDialog(this,
                "No game data to generate report from!",
                "No Data",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Get selected format
            String format = (String) formatCombo.getSelectedItem();
            
            // Generate report through controller
            File reportFile = controller.generateReport(format);
            
            // Show success message
            String message = String.format(
                "Report generated successfully!\n\n" +
                "Format: %s\n" +
                "Filename: %s\n" +
                "Total Turns: %d",
                format,
                reportFile.getName(),
                controller.getTotalTurns()
            );
            
            JOptionPane.showMessageDialog(this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error generating report: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleExit() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the application?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}