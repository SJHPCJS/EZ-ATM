/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package ui.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import ui.atm.LoginFrame;
import ui.simulator.DemoEnvironment;
import util.Data;
import pojo.Account;
import util.strategy.impl.ConsistentStrategy;

/**
 * Controller class for the EZ ATM application.
 * Manages the main GUI interface and provides functionality
 * for refreshing account data, starting demo environments,
 * and launching the single ATM interface.
 */
public class Controller extends JFrame {
    private static Controller controller;
    private JPanel accountsPanel;

    /**
     * Singleton method to get the instance of Controller.
     *
     * @return The singleton instance of Controller
     */
    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    /**
     * Constructor for the Controller class.
     * Sets up the main GUI interface with buttons and account display panel.
     */
    public Controller() {
        setTitle("EZ ATM Controller");
        setSize(720, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Save data to file on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Data.writeDataToLocalFile("data.txt");
            }
        });

        // Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Title label setup
        JLabel titleLabel = new JLabel("EZ ATM Controller", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.getHSBColor(0.568F, 0.314F, 0.949F));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(720, 100));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Accounts panel setup
        accountsPanel = new JPanel();
        accountsPanel.setBackground(Color.WHITE);
        accountsPanel.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(accountsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons setup
        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener((e)->{
            dispose();
            refreshData();
            setVisible(true);
        });
        JButton demoDataRaceButton = new JButton("Auto Demo Data Race");
        demoDataRaceButton.addActionListener((e)->{
            dispose();
            String exePath = "demo_inconsistent.exe";
            Runtime runtime = Runtime.getRuntime();
            try {
                Process process = runtime.exec(exePath);
                process.waitFor();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
            Data.refresh();
            refreshData();
            setVisible(true);
        });
        JButton demoNoDataRaceButton = new JButton("Auto Demo No Data Race");
        demoNoDataRaceButton.addActionListener((e)->{
            dispose();
            String exePath = "demo_consistent.exe";
            Runtime runtime = Runtime.getRuntime();
            try {
                Process process = runtime.exec(exePath);
                process.waitFor();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
            Data.refresh();
            refreshData();
            setVisible(true);
        });
        JButton manualButton = new JButton("Single ATM");
        manualButton.addActionListener((e) -> {
            dispose();
            new LoginFrame(new ConsistentStrategy());
        });

        // Set fonts for buttons
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 20));
        demoDataRaceButton.setFont(new Font("Arial", Font.PLAIN, 20));
        demoNoDataRaceButton.setFont(new Font("Arial", Font.PLAIN, 20));
        manualButton.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add buttons to button panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(refreshButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(demoDataRaceButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(demoNoDataRaceButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(manualButton, gbc);

        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Initialize data refresh on startup
        refreshData();
    }

    /**
     * Refreshes the displayed account information.
     * Retrieves the latest account data from the data source
     * and updates the GUI accordingly.
     */
    public void refreshData() {
        List<Account> accounts = Data.getInstance().getAccounts();
        accountsPanel.removeAll();
        boolean isAlternate = false;
        Color lightBlue = new Color(224, 255, 255);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        int y = 0;
        for (Account account : accounts) {
            JPanel accountPanel = new JPanel();
            accountPanel.setLayout(new BorderLayout());
            accountPanel.setBackground(isAlternate ? lightBlue : Color.WHITE);
            JLabel accountLabel = new JLabel("Account Number: " + account.getAccountNum() +
                    ", PIN: " + account.getPIN() +
                    ", Username: " + account.getUsername() +
                    ", Balance: " + account.getBalance());
            accountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            accountPanel.add(accountLabel, BorderLayout.CENTER);
            gbc.gridy = y++;
            accountsPanel.add(accountPanel, gbc);
            isAlternate = !isAlternate;
        }
        accountsPanel.revalidate();
        accountsPanel.repaint();
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Start the main controller GUI
                Controller.getInstance().setVisible(true);
            }
        });
    }
}
