/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package ui.atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import util.Data;
import util.strategy.AccountOperationStrategy;

/**
 * JFrame subclass for handling the transfer functionality in an ATM interface.
 */
public class TransferFrame extends JFrame {
    private final JPanel mainPanel;
    private final JPanel accountPanel;
    private final JPanel amountPanel;
    private JTextField accountField;
    private JTextField amountField;
    private NumKeyBoardFrame numKeyBoardFrame;
    private final String userName;
    private final String fromAccountNumber;
    private String toAccountNumber;
    private final AccountOperationStrategy accountOperationStrategy;

    /**
     * Constructs a TransferFrame instance.
     *
     * @param userName                The username initiating the transfer.
     * @param fromAccountNumber       The account number from which the transfer is initiated.
     * @param accountOperationStrategy The strategy for account operations (e.g., transfer).
     */
    public TransferFrame(String userName, String fromAccountNumber, AccountOperationStrategy accountOperationStrategy) {
        this.userName = userName;
        this.fromAccountNumber = fromAccountNumber;
        this.accountOperationStrategy = accountOperationStrategy;
        mainPanel = new JPanel(new CardLayout());
        accountPanel = createAccountPanel();
        amountPanel = createAmountPanel();

        mainPanel.add(accountPanel, "AccountPanel");
        mainPanel.add(amountPanel, "AmountPanel");

        initialize();
        setVisible(true);
    }

    /**
     * Initializes the TransferFrame by setting attributes and adding components.
     */
    private void initialize() {
        UIUtil.setMainFrameAttribute(this);
        UIUtil.setMainFrameLocation(this);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the panel for entering the destination account number.
     *
     * @return The JPanel for entering the destination account number.
     */
    private JPanel createAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome " + userName);
        welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel, gbc);

        // Spacer to push the account fields down to the center
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        panel.add(Box.createVerticalStrut(50), gbc);

        // Account Label
        JLabel accountLabel = new JLabel("Please enter the account number for your transfer:");
        accountLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0;
        panel.add(accountLabel, gbc);

        // Account Field
        accountField = new JTextField(20);
        accountField.setEnabled(false);
        gbc.gridy = 3;
        panel.add(accountField, gbc);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if (numKeyBoardFrame == null || !numKeyBoardFrame.isVisible()) {
                    numKeyBoardFrame = new NumKeyBoardFrame(TransferFrame.this, new NumKeyBoardListener() {
                        @Override
                        public void onConfirm(String value, boolean shouldSwitch) {
                            accountField.setText(value);
                            if (shouldSwitch && isAccountValid(value)) {
                                if (value.equals(fromAccountNumber)) {
                                    JOptionPane.showMessageDialog(TransferFrame.this, "You cannot transfer to your own account.", "Error", JOptionPane.ERROR_MESSAGE);;
                                } else {
                                    toAccountNumber = value;
                                    numKeyBoardFrame.dispose();
                                    switchToAmountPanel();
                                }
                            } else if (shouldSwitch) {
                                JOptionPane.showMessageDialog(TransferFrame.this, "Invalid account number.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        @Override
                        public void onInput(String value) {
                            accountField.setText(value);
                        }
                    });
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                numKeyBoardFrame.dispose();
            }
        });

        // Spacer to push the components to the center
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    /**
     * Creates the panel for entering the transfer amount.
     *
     * @return The JPanel for entering the transfer amount.
     */
    private JPanel createAmountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome " + userName);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel, gbc);

        // Spacer to push the account fields down to the center
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        panel.add(Box.createVerticalStrut(50), gbc);

        // Amount Label
        JLabel amountLabel = new JLabel("Please enter the transfer amount:");
        amountLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0;
        panel.add(amountLabel, gbc);

        // Amount Field
        amountField = new JTextField(20);
        accountField.setEnabled(false);
        gbc.gridy = 3;
        panel.add(amountField, gbc);

        // Spacer to push the components to the center
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    /**
     * Switches the main panel to the amount panel.
     */
    private void switchToAmountPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "AmountPanel");

        numKeyBoardFrame = new NumKeyBoardFrame(TransferFrame.this, new NumKeyBoardListener() {
            @Override
            public void onConfirm(String value, boolean shouldSwitch) {
                amountField.setText(value);
                if (shouldSwitch && isAmountValid(value)) {
                    accountOperationStrategy.transfer(fromAccountNumber, toAccountNumber, Double.parseDouble(value));
                    JOptionPane.showMessageDialog(TransferFrame.this, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new IndexFrame(userName, fromAccountNumber, accountOperationStrategy);
                } else if (shouldSwitch) {
                    JOptionPane.showMessageDialog(TransferFrame.this, "Invalid amount. Please enter a value up to 8 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onInput(String value) {
                amountField.setText(value);
            }
        });
    }

    /**
     * Checks if the provided account number is valid.
     *
     * @param account The account number to validate.
     * @return true if the account number is valid, false otherwise.
     */
    private boolean isAccountValid(String account) {
        return account.matches("\\d{6}") && Data.getInstance().getAccounts().stream().anyMatch(a -> a.getAccountNum().equals(account));
    }

    /**
     * Checks if the provided amount is valid.
     *
     * @param amount The amount to validate.
     * @return true if the amount is valid, false otherwise.
     */
    private boolean isAmountValid(String amount) {
        return amount.matches("\\d{1,8}") && Double.parseDouble(amount) <= Data.getInstance().getBalanceByAccountNum(fromAccountNumber);
    }
}
