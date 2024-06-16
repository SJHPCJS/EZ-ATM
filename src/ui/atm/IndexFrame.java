/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package ui.atm;

import ui.main.Controller;
import util.Data;
import util.strategy.AccountOperationStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * IndexFrame class represents the main interface of the ATM system,
 * displaying welcome messages and providing operation options.
 * Users can choose from operations such as transfer, deposit, withdraw, or balance inquiry.
 */
public class IndexFrame extends JFrame {
    private final AccountOperationStrategy strategy;
    private double balance;

    /**
     * Constructor to initialize the IndexFrame interface.
     *
     * @param username Username of the user
     * @param accountNum Account number
     * @param strategy Account operation strategy
     */
    public IndexFrame(String username, String accountNum, AccountOperationStrategy strategy) {
        this.strategy = strategy;
        this.balance = Data.getInstance().getBalanceByAccountNum(accountNum);
        initialize(welcomePanel(username,accountNum));
        setVisible(true);
    }

    /**
     * Method to initialize the interface, setting JFrame attributes and adding the main panel.
     *
     * @param panel Main panel to add
     */
    public void initialize(JPanel panel) {
        this.getContentPane().removeAll();  // Clear existing content
        UIUtil.setMainFrameAttribute(this); // Set basic attributes of the main frame
        UIUtil.setMainFrameLocation(this); // Set the position of the main frame
        this.add(panel, BorderLayout.CENTER); // Add the main panel to the center
        this.revalidate();  // Revalidate the container
        this.repaint();  // Repaint the container
    }

    /**
     * Welcome panel displaying welcome message and operation options.
     *
     * @param username Username of the user
     * @param accountNum Account number
     * @return JPanel Welcome panel
     */
    private JPanel welcomePanel(String username, String accountNum) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(720, 500));
        JPanel welcomeMessage = new JPanel();
        welcomeMessage.setPreferredSize(new Dimension(720, 100));

        JLabel welcomeMessage1 = new JLabel("Welcome " + username);
        welcomeMessage1.setPreferredSize(new Dimension(720, 50));
        welcomeMessage1.setFont(new Font("Serif", Font.PLAIN, 50));
        welcomeMessage1.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMessage1.setVerticalAlignment(SwingConstants.CENTER);
        welcomeMessage.add(welcomeMessage1);

        JLabel welcomeMessage2 = new JLabel("Please select your service");
        welcomeMessage2.setPreferredSize(new Dimension(720, 50));
        welcomeMessage2.setFont(new Font("Serif", Font.PLAIN, 20));
        welcomeMessage2.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMessage2.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeMessage2, BorderLayout.CENTER);
        welcomeMessage.add(welcomeMessage2);

        mainPanel.add(welcomeMessage, BorderLayout.NORTH);

        JPanel buttonPane = new JPanel(new BorderLayout());
        buttonPane.setPreferredSize(new Dimension(720, 500));
        JPanel buttonPanelLeft = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton[] buttons1 = new JButton[]{new JButton("Transfer"), new JButton("2"), new JButton("3"), new JButton("Inquire")};

        buttons1[0].setPreferredSize(new Dimension(150, 60));
        buttons1[0].addActionListener((e) -> {
            dispose();
            new TransferFrame(username, accountNum, strategy);
        });
        buttonPanelLeft.add(buttons1[0]);
        buttons1[1].setVisible(false);
        buttons1[1].setPreferredSize(new Dimension(150, 60));
        buttons1[1].addActionListener((e) -> System.out.println("2"));
        buttonPanelLeft.add(buttons1[1]);
        buttons1[2].setVisible(false);
        buttons1[2].setPreferredSize(new Dimension(150, 60));
        buttons1[2].addActionListener((e) -> System.out.println("3"));
        buttonPanelLeft.add(buttons1[2]);
        buttons1[3].setPreferredSize(new Dimension(150, 60));
        buttons1[3].addActionListener((e) -> initialize(informationPanel(username,accountNum)));
        buttonPanelLeft.add(buttons1[3]);

        buttonPanelLeft.setPreferredSize(new Dimension(150, 500));
        buttonPane.add(buttonPanelLeft, BorderLayout.WEST);

        JPanel buttonPanelRight = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton[] buttons2 = new JButton[]{new JButton("Deposit"), new JButton("6"), new JButton("7"), new JButton("Withdraw")};

        buttons2[0].setPreferredSize(new Dimension(150, 60));
        buttons2[0].addActionListener((e) -> initialize(depositPanel(username, accountNum)));
        buttonPanelRight.add(buttons2[0]);
        buttons2[1].setVisible(false);
        buttons2[1].setPreferredSize(new Dimension(150, 60));
        buttons2[1].addActionListener((e) -> System.out.println("6"));
        buttonPanelRight.add(buttons2[1]);
        buttons2[2].setVisible(false);
        buttons2[2].setPreferredSize(new Dimension(150, 60));
        buttons2[2].addActionListener((e) -> System.out.println("7"));
        buttonPanelRight.add(buttons2[2]);
        buttons2[3].setPreferredSize(new Dimension(150, 60));
        buttons2[3].addActionListener((e) -> initialize(withdrawPanel(username, accountNum)));
        buttonPanelRight.add(buttons2[3]);

        buttonPanelRight.setPreferredSize(new Dimension(150, 500));
        buttonPane.add(buttonPanelRight, BorderLayout.EAST);

        mainPanel.add(buttonPane, BorderLayout.CENTER);

        JButton exitJButton = new JButton("Exit");
        exitJButton.setForeground(Color.RED);
        exitJButton.setPreferredSize(new Dimension(150, 60));

        JPanel exitJButtonPanel = new JPanel();
        exitJButtonPanel.setLayout(new BorderLayout());
        exitJButtonPanel.add(exitJButton, BorderLayout.WEST);
        exitJButton.addActionListener((e) -> {
            dispose();
            Controller.getInstance().setVisible(true);
        });
        mainPanel.add(exitJButtonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    /**
     * Deposit panel where users can enter deposit amount or choose predefined amounts.
     *
     * @param username Username of the user
     * @param accountNum Account number
     * @return JPanel Deposit panel
     */
    private JPanel depositPanel(String username, String accountNum) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(720, 700));
        JPanel welcomeMessage = new JPanel();
        welcomeMessage.setPreferredSize(new Dimension(720, 150));

        JLabel depositMessage1 = new JLabel("Please enter the amount you want to deposit:");
        depositMessage1.setPreferredSize(new Dimension(720, 50));
        depositMessage1.setFont(new Font("Serif", Font.PLAIN, 30));
        depositMessage1.setHorizontalAlignment(SwingConstants.CENTER);
        depositMessage1.setVerticalAlignment(SwingConstants.CENTER);
        welcomeMessage.add(depositMessage1);

        JLabel depositMessage2 = new JLabel("Your balance is ￡" + balance + ".");
        depositMessage2.setPreferredSize(new Dimension(720, 50));
        depositMessage2.setFont(new Font("Serif", Font.PLAIN, 20));
        depositMessage2.setHorizontalAlignment(SwingConstants.CENTER);
        depositMessage2.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(depositMessage2, BorderLayout.CENTER);
        welcomeMessage.add(depositMessage2);

        AtomicInteger depositAmount = new AtomicInteger();
        JTextField depositField = new JTextField(depositAmount.get());
        depositField.setPreferredSize(new Dimension(200, 50));
        depositField.setFont(new Font("Serif", Font.PLAIN, 30));
        depositField.setForeground(Color.black);
        depositField.setHorizontalAlignment(SwingConstants.CENTER);
        depositField.setEnabled(false);
        welcomeMessage.add(depositField);

        mainPanel.add(welcomeMessage, BorderLayout.NORTH);

        JPanel buttonPane = new JPanel(new BorderLayout());
        buttonPane.setPreferredSize(new Dimension(720, 500));
        JPanel buttonPanelLeft = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton[] buttons1 = new JButton[]{new JButton("Back"), new JButton("￡10"), new JButton("￡20"), new JButton("￡40")};

        buttons1[0].setPreferredSize(new Dimension(150, 60));
        buttons1[0].addActionListener((e) -> initialize(welcomePanel(username,accountNum)));
        buttonPanelLeft.add(buttons1[0]);
        buttons1[1].setPreferredSize(new Dimension(150, 60));
        buttons1[1].addActionListener((e) -> {
            depositAmount.set(10);
            depositField.setText(String.valueOf(depositAmount.get()));
        });
        buttonPanelLeft.add(buttons1[1]);
        buttons1[2].setPreferredSize(new Dimension(150, 60));
        buttons1[2].addActionListener((e) -> {
            depositAmount.set(20);
            depositField.setText(String.valueOf(depositAmount.get()));
        });
        buttonPanelLeft.add(buttons1[2]);
        buttons1[3].setPreferredSize(new Dimension(150, 60));
        buttons1[3].addActionListener((e) -> {
            depositAmount.set(40);
            depositField.setText(String.valueOf(depositAmount.get()));
        });
        buttonPanelLeft.add(buttons1[3]);

        buttonPanelLeft.setPreferredSize(new Dimension(150, 500));
        buttonPane.add(buttonPanelLeft, BorderLayout.WEST);

        JPanel buttonPanelRight = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton[] buttons2 = new JButton[]{new JButton("￡100"), new JButton("￡500"), new JButton("Delete")};

        buttons2[0].setPreferredSize(new Dimension(150, 60));
        buttons2[0].addActionListener((e) -> {
            depositAmount.set(100);
            depositField.setText(String.valueOf(depositAmount.get()));
        });
        buttonPanelRight.add(buttons2[0]);

        buttons2[1].setPreferredSize(new Dimension(150, 60));
        buttons2[1].addActionListener((e) -> {
            depositAmount.set(500);
            depositField.setText(String.valueOf(depositAmount.get()));
        });
        buttonPanelRight.add(buttons2[1]);
        buttons2[2].setPreferredSize(new Dimension(150, 60));
        buttons2[2].addActionListener((e) -> {
            depositAmount.set(0);
            depositField.setText(String.valueOf(depositAmount.get()));
        });
        buttonPanelRight.add(buttons2[2]);

        buttonPanelRight.setPreferredSize(new Dimension(150, 500));
        buttonPane.add(buttonPanelRight, BorderLayout.EAST);

        mainPanel.add(buttonPane, BorderLayout.CENTER);

        JButton enterJButton = new JButton("Confirm");
        enterJButton.setForeground(Color.BLACK);
        enterJButton.setPreferredSize(new Dimension(150, 60));

        JPanel enterJButtonPanel = new JPanel();
        enterJButtonPanel.setLayout(new BorderLayout());
        enterJButtonPanel.add(enterJButton, BorderLayout.WEST);
        enterJButton.addActionListener((e) -> {
            if (depositAmount.get() >= Data.getInstance().getBalanceByAccountNum(accountNum)) {
                JOptionPane.showMessageDialog(IndexFrame.this, "Deposit amount exceeds balance.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Entered amount: " + depositAmount);
                balance += depositAmount.get();
                strategy.deposit(accountNum, depositAmount.get());
                JOptionPane.showMessageDialog(IndexFrame.this, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                initialize(welcomePanel(username, accountNum));
            }
        });
        mainPanel.add(enterJButtonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    /**
     * Withdraw panel where users can enter withdrawal amount or choose predefined amounts.
     *
     * @param username Username of the user
     * @param accountNum Account number
     * @return JPanel Withdraw panel
     */
    private JPanel withdrawPanel(String username, String accountNum) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(720, 700));
        JPanel welcomeMessage = new JPanel();
        welcomeMessage.setPreferredSize(new Dimension(720, 150));

        JLabel withdrawMessage1 = new JLabel("Please enter the amount you want to withdraw:");
        withdrawMessage1.setPreferredSize(new Dimension(720, 50));
        withdrawMessage1.setFont(new Font("Serif", Font.PLAIN, 30));
        withdrawMessage1.setHorizontalAlignment(SwingConstants.CENTER);
        withdrawMessage1.setVerticalAlignment(SwingConstants.CENTER);
        welcomeMessage.add(withdrawMessage1);

        JLabel withdrawMessage2 = new JLabel("Your balance is ￡" + balance + ".");
        withdrawMessage2.setPreferredSize(new Dimension(720, 50));
        withdrawMessage2.setFont(new Font("Serif", Font.PLAIN, 20));
        withdrawMessage2.setHorizontalAlignment(SwingConstants.CENTER);
        withdrawMessage2.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(withdrawMessage2, BorderLayout.CENTER);
        welcomeMessage.add(withdrawMessage2);

        AtomicInteger withdrawAmount = new AtomicInteger();
        JTextField withdrawField = new JTextField(withdrawAmount.get());
        withdrawField.setPreferredSize(new Dimension(200, 50));
        withdrawField.setFont(new Font("Serif", Font.PLAIN, 30));
        withdrawField.setForeground(Color.black);
        withdrawField.setHorizontalAlignment(SwingConstants.CENTER);
        withdrawField.setEnabled(false);
        welcomeMessage.add(withdrawField);

        mainPanel.add(welcomeMessage, BorderLayout.NORTH);

        JPanel buttonPane = new JPanel(new BorderLayout());
        buttonPane.setPreferredSize(new Dimension(720, 500));
        JPanel buttonPanelLeft = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton[] buttons1 = new JButton[]{new JButton("Back"), new JButton("￡10"), new JButton("￡20"), new JButton("￡40")};

        buttons1[0].setPreferredSize(new Dimension(150, 60));
        buttons1[0].addActionListener((e) -> initialize(welcomePanel(username,accountNum)));
        buttonPanelLeft.add(buttons1[0]);
        buttons1[1].setPreferredSize(new Dimension(150, 60));
        buttons1[1].addActionListener((e) -> {
            withdrawAmount.set(10);
            withdrawField.setText(String.valueOf(withdrawAmount.get()));
        });
        buttonPanelLeft.add(buttons1[1]);
        buttons1[2].setPreferredSize(new Dimension(150, 60));
        buttons1[2].addActionListener((e) -> {
            withdrawAmount.set(20);
            withdrawField.setText(String.valueOf(withdrawAmount.get()));
        });
        buttonPanelLeft.add(buttons1[2]);
        buttons1[3].setPreferredSize(new Dimension(150, 60));
        buttons1[3].addActionListener((e) -> {
            withdrawAmount.set(40);
            withdrawField.setText(String.valueOf(withdrawAmount.get()));
        });
        buttonPanelLeft.add(buttons1[3]);

        buttonPanelLeft.setPreferredSize(new Dimension(150, 500));
        buttonPane.add(buttonPanelLeft, BorderLayout.WEST);

        JPanel buttonPanelRight = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton[] buttons2 = new JButton[]{new JButton("￡100"), new JButton("￡500"), new JButton("Delete"), new JButton("Exit")};

        buttons2[0].setPreferredSize(new Dimension(150, 60));
        buttons2[0].addActionListener((e) -> {
            withdrawAmount.set(100);
            withdrawField.setText(String.valueOf(withdrawAmount.get()));
        });
        buttonPanelRight.add(buttons2[0]);

        buttons2[1].setPreferredSize(new Dimension(150, 60));
        buttons2[1].addActionListener((e) -> {
            withdrawAmount.set(500);
            withdrawField.setText(String.valueOf(withdrawAmount.get()));
        });
        buttonPanelRight.add(buttons2[1]);
        buttons2[2].setPreferredSize(new Dimension(150, 60));
        buttons2[2].addActionListener((e) -> {
            withdrawAmount.set(0);
            withdrawField.setText(String.valueOf(withdrawAmount.get()));
        });
        buttonPanelRight.add(buttons2[2]);

        buttonPanelRight.setPreferredSize(new Dimension(150, 500));
        buttonPane.add(buttonPanelRight, BorderLayout.EAST);

        mainPanel.add(buttonPane, BorderLayout.CENTER);

        JButton enterJButton = new JButton("Confirm");
        enterJButton.setForeground(Color.BLACK);
        enterJButton.setPreferredSize(new Dimension(150, 60));

        JPanel enterJButtonPanel = new JPanel();
        enterJButtonPanel.setLayout(new BorderLayout());
        enterJButtonPanel.add(enterJButton, BorderLayout.WEST);
        enterJButton.addActionListener((e) -> {
            System.out.println("Entered amount: " + withdrawAmount);
            balance -= withdrawAmount.get();
            strategy.withdraw(accountNum, withdrawAmount.get());
            JOptionPane.showMessageDialog(IndexFrame.this, "Withdraw successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            initialize(welcomePanel(username, accountNum));
        });
        mainPanel.add(enterJButtonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    /**
     * Generates a JPanel displaying account information for a specific user.
     *
     * @param username The username of the account holder.
     * @param accountNum The account number associated with the account.
     * @return JPanel containing account information and navigation buttons.
     */
    private JPanel informationPanel(String username, String accountNum) {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(720,700));
        JPanel informationMessage = new JPanel();
        informationMessage.setPreferredSize(new Dimension(720,100));

        JLabel informationMessage1 = new JLabel("Your account balance information");
        informationMessage1.setPreferredSize(new Dimension(720,50));
        informationMessage1.setFont(new Font("Serif", Font.PLAIN, 50));
        informationMessage1.setHorizontalAlignment(SwingConstants.CENTER);
        informationMessage1.setVerticalAlignment(SwingConstants.CENTER);
        informationMessage.add(informationMessage1);

        mainPanel.add(informationMessage,BorderLayout.NORTH);

        JPanel centralPane = new JPanel(new BorderLayout());
        centralPane.setPreferredSize(new Dimension(720,500));
        JPanel buttonPanelLeft = new JPanel(new GridLayout(4,1,10,10));
        JButton[] buttons1 = new JButton[]{new JButton("Transfer"), new JButton("2"), new JButton("3"), new JButton("Back")};

        buttons1[0].setPreferredSize(new Dimension(150,60));
        buttons1[0].addActionListener((e) -> {
            dispose();
            new TransferFrame(username, accountNum, strategy);
        });
        buttonPanelLeft.add(buttons1[0]);
        buttons1[1].setVisible(false);
        buttons1[1].setPreferredSize(new Dimension(150,60));
        buttons1[1].addActionListener((e)-> System.out.println("2"));
        buttonPanelLeft.add(buttons1[1]);
        buttons1[2].setVisible(false);
        buttons1[2].setPreferredSize(new Dimension(150,60));
        buttons1[2].addActionListener((e)-> System.out.println("3"));
        buttonPanelLeft.add(buttons1[2]);
        buttons1[3].setPreferredSize(new Dimension(150,60));
        buttons1[3].addActionListener((e) -> initialize(welcomePanel(username,accountNum)));
        buttonPanelLeft.add(buttons1[3]);

        buttonPanelLeft.setPreferredSize(new Dimension(150,500));
        centralPane.add(buttonPanelLeft,BorderLayout.WEST);

        JPanel buttonPanelRight = new JPanel(new GridLayout(4,1,10,10));
        JButton[] buttons2 = new JButton[]{new JButton("Deposit"), new JButton("6"), new JButton("7"), new JButton("Withdraw")};

        buttons2[0].setPreferredSize(new Dimension(150,60));
        buttons2[0].addActionListener((e) -> initialize(depositPanel(username, accountNum)));
        buttonPanelRight.add(buttons2[0]);
        buttons2[1].setVisible(false);
        buttons2[1].setPreferredSize(new Dimension(150,60));
        buttons2[1].addActionListener((e)-> System.out.println("6"));
        buttonPanelRight.add(buttons2[1]);
        buttons2[2].setVisible(false);
        buttons2[2].setPreferredSize(new Dimension(150,60));
        buttons2[2].addActionListener((e)-> System.out.println("7"));
        buttonPanelRight.add(buttons2[2]);
        buttons2[3].setPreferredSize(new Dimension(150,60));
        buttons2[3].addActionListener((e)-> System.out.println("8"));
        buttonPanelRight.add(buttons2[3]);

        buttonPanelRight.setPreferredSize(new Dimension(150,500));
        centralPane.add(buttonPanelRight,BorderLayout.EAST);

        mainPanel.add(centralPane,BorderLayout.CENTER);

        JButton exitJButton = new JButton("Exit");
        exitJButton.setForeground(Color.RED);
        exitJButton.setPreferredSize(new Dimension(150,60));

        JPanel exitJButtonPanel = new JPanel();
        exitJButtonPanel.setLayout(new BorderLayout());
        exitJButtonPanel.add(exitJButton, BorderLayout.WEST);
        exitJButton.addActionListener((e)-> {
            dispose();
            Controller.getInstance().setVisible(true);
        });
        mainPanel.add(exitJButtonPanel, BorderLayout.SOUTH);

        JPanel informationPanel = new JPanel(new GridLayout(4,1,10,10));
        informationPanel.setBackground(new Color(173, 216, 230));
        JLabel[] information = new JLabel[]{new JLabel("Account Number: " + accountNum), new JLabel("Account Balance: " + balance), new JLabel("Account Type: Saving"), new JLabel("Account Status: Active")};
        for (JLabel label : information) {
            label.setFont(new Font("Serif", Font.PLAIN, 20));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            informationPanel.add(label);
        }
        informationPanel.setPreferredSize(new Dimension(400,200));
        centralPane.add(informationPanel, BorderLayout.CENTER);
        return mainPanel;
    }
}
