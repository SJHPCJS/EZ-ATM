/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package ui.atm;

import pojo.Account;
import ui.main.Controller;
import util.Data;
import util.strategy.AccountOperationStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame extends JFrame {
    private final JPanel mainPanel; // Main panel containing account and PIN panels
    private final JPanel accountPanel; // Panel for account number input
    private final JPanel pinPanel; // Panel for PIN input
    private final AccountOperationStrategy strategy; // Strategy for account operations
    private JTextField accountField; // Text field for entering account number
    private JPasswordField pinField; // Password field for entering PIN
    private NumKeyBoardFrame numKeyBoardFrame; // On-screen keyboard frame
    private Account account; // Account object for the entered account details
    private Account accountWithFullDetail; // Account object with full account details
    private boolean openController = true; // Flag to control whether the controller window should be opened

    /**
     * Constructor to initialize LoginFrame with the provided strategy.
     *
     * @param strategy the account operation strategy to be used
     */
    public LoginFrame(AccountOperationStrategy strategy) {
        this.strategy = strategy;
        mainPanel = new JPanel(new CardLayout());
        accountPanel = createAccountPanel();
        pinPanel = createPinPanel();

        mainPanel.add(accountPanel, "AccountPanel");
        mainPanel.add(pinPanel, "PinPanel");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                numKeyBoardFrame.dispose();
                if (openController) {
                    Controller.getInstance().setVisible(true);
                }
            }
        });

        initialize();
        setVisible(true);
    }

    /**
     * Initializes the main frame settings.
     */
    private void initialize() {
        UIUtil.setMainFrameAttribute(this);
        UIUtil.setMainFrameLocation(this);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the panel for entering the account number.
     *
     * @return the account panel
     */
    private JPanel createAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Hello from EZ ATM!");
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

        // Account Label
        JLabel accountLabel = new JLabel("Please enter your account number:");
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
                    numKeyBoardFrame = new NumKeyBoardFrame(LoginFrame.this, new NumKeyBoardListener() {
                        @Override
                        public void onConfirm(String value, boolean shouldSwitch) {
                            accountField.setText(value);
                            if (shouldSwitch && isAccountValid(value)) {
                                numKeyBoardFrame.dispose();
                                switchToPinPanel();
                            } else if (shouldSwitch) {
                                JOptionPane.showMessageDialog(LoginFrame.this, "Invalid account number.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        @Override
                        public void onInput(String value) {
                            accountField.setText(value);
                        }
                    });
                }
            }
        });

        // Spacer to push the components to the center
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    /**
     * Creates the panel for entering the PIN.
     *
     * @return the PIN panel
     */
    private JPanel createPinPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Hello from EZ ATM!");
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

        // PIN Label
        JLabel pinLabel = new JLabel("Please enter PIN of your account:");
        pinLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0;
        panel.add(pinLabel, gbc);

        // PIN Field
        pinField = new JPasswordField(20);
        pinField.setEnabled(false);
        gbc.gridy = 3;
        panel.add(pinField, gbc);

        // Spacer to push the components to the center
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    /**
     * Switches the view to the PIN input panel.
     */
    private void switchToPinPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "PinPanel");
        numKeyBoardFrame = new NumKeyBoardFrame(LoginFrame.this, new NumKeyBoardListener() {
            @Override
            public void onConfirm(String value, boolean shouldSwitch) {
                pinField.setText(value);
                account = new Account(accountField.getText(),pinField.getText());
                if (shouldSwitch && isPINValid(value) && checkLogin(account.getAccountNum(), account.getPIN())) {
                    accountWithFullDetail = initializeAccountWithDetails(account.getAccountNum(),account.getPIN());
                    new IndexFrame(accountWithFullDetail.getUsername(), accountWithFullDetail.getAccountNum(),strategy).setVisible(true);
                    openController = false;
                    numKeyBoardFrame.dispose();
                    dispose();
                } else if (shouldSwitch && !isPINValid(value)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid PIN. Please enter a 4-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (shouldSwitch && isPINValid(value) && !checkLogin(account.getAccountNum(), account.getPIN())){
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid account. Please enter the correct account and PIN.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onInput(String value) {
                pinField.setText(value);
            }
        });
    }

    /**
     * Checks if the entered account number is valid.
     *
     * @param account the account number to validate
     * @return true if the account number is valid, false otherwise
     */
    private boolean isAccountValid(String account) {
        return account.matches("\\d{6}") && Data.getInstance().getAccounts().stream().anyMatch(a -> a.getAccountNum().equals(account));
    }

    /**
     * Checks if the entered PIN is valid.
     *
     * @param pin the PIN to validate
     * @return true if the PIN is valid, false otherwise
     */
    private boolean isPINValid(String pin) {
        return pin.matches("\\d{4}");
    }

    /**
     * Checks if the login credentials are correct.
     *
     * @param accountNum the account number
     * @param pin the PIN
     * @return true if the account number and PIN match, false otherwise
     */
    private boolean checkLogin(String accountNum, String pin) {
        for (Account account : Data.getInstance().getAccounts()) {
            if (account.getAccountNum().equals(accountNum) && account.getPIN().equals(pin)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes the account with full details.
     *
     * @param accountNum the account number
     * @param pin the PIN
     * @return the account with full details
     */
    private Account initializeAccountWithDetails(String accountNum, String pin) {
        Account newAccount = new Account(accountNum, pin, "", 0.0);

        // Find Accounts with matching accounts and passwords in the data
        for (Account account : Data.getInstance().getAccounts()) {
            if (account.getAccountNum().equals(accountNum) && account.getPIN().equals(pin)) {
                // If a matching Account is found, reinitialise the rest of the newAccount properties with its data.
                newAccount.setUsername(account.getUsername());
                newAccount.setBalance(account.getBalance());
                break;
            }
        }
        return newAccount;
    }
}
