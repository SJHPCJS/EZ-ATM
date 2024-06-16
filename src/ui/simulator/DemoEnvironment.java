package ui.simulator;

import ui.atm.UIUtil;
import util.Data;
import util.strategy.AccountOperationStrategy;
import util.strategy.impl.ConsistentStrategy;
import util.strategy.impl.InconsistentStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoEnvironment {
    private JFrame[] demoFrames;
    private String username = "TestAccount1";
    private String account = "000001";
    private boolean isConsistent;

    public DemoEnvironment(boolean isConsistent) {
        this.isConsistent = isConsistent;
        demoFrames = new JFrame[2];
    }

    public void startDemo() {
        prepareDemoEnv();
        automateActions();
    }

    private void prepareDemoEnv() {
        String username = "TestAccount1";
        String account = "000001";
        initializeDepositDemoFrame(account);
        initializeWithdrawDemoFrame(account);
        for (JFrame demoFrame : demoFrames) {
            demoFrame.setVisible(true);
        }
    }

    private JPanel depositPanel(String accountNum) {
        AccountOperationStrategy accountOperationStrategy = (isConsistent)?new ConsistentStrategy():new InconsistentStrategy();
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

        JLabel depositMessage2 = new JLabel("Your balance is ￡1000" + ".");
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
                JOptionPane.showMessageDialog(this.demoFrames[0], "Deposit amount exceeds balance.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                new Thread(()->accountOperationStrategy.deposit(accountNum, depositAmount.get())).start();
            }
        });
        mainPanel.add(enterJButtonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    public void initializeDepositDemoFrame(String accountNum) {
        demoFrames[0] = new JFrame();
        UIUtil.setMainFrameAttribute(demoFrames[0]);
        UIUtil.setMainFrameLocation(demoFrames[0]);
        UIUtil.setTestFrameLocation(demoFrames[0],"left");
        demoFrames[0].add(depositPanel(accountNum));
    }

    private JPanel withdrawPanel(String accountNum) {
        AccountOperationStrategy accountOperationStrategy = (isConsistent)?new ConsistentStrategy():new InconsistentStrategy();
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

            JLabel withdrawMessage2 = new JLabel("Your balance is ￡1000"  + ".");
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
                new Thread(()->accountOperationStrategy.withdraw(accountNum, withdrawAmount.get())).start();

            });
            mainPanel.add(enterJButtonPanel, BorderLayout.SOUTH);
            return mainPanel;
    }
    private void initializeWithdrawDemoFrame(String accountNum) {
        demoFrames[1] = new JFrame();
        UIUtil.setMainFrameAttribute(demoFrames[1]);
        UIUtil.setMainFrameLocation(demoFrames[1]);
        UIUtil.setTestFrameLocation(demoFrames[1],"right");
        demoFrames[1].add(withdrawPanel(accountNum));
    }

    private void automateActions() {
        try {
            Robot robot = new Robot();
            JOptionPane.showMessageDialog(null, "The demo will start. Please do not move the mouse during the demo.\n \n" + "The current balance is " + Data.getInstance().getBalanceByAccountNum(account) , "Demo Starts", JOptionPane.INFORMATION_MESSAGE);
            // Simulate click on £100 button in deposit window
            JFrame depositFrame = demoFrames[0];
            Point depositButtonLocation = getRelativeLocation(depositFrame, 100);
            robot.mouseMove(depositButtonLocation.x, depositButtonLocation.y);
            Thread.sleep(500); // Add delay for visibility
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            // Simulate click on £40 button in withdraw window
            JFrame withdrawFrame = demoFrames[1];
            Point withdrawButtonLocation = getRelativeLocation(withdrawFrame, 40);
            robot.mouseMove(withdrawButtonLocation.x, withdrawButtonLocation.y);
            Thread.sleep(500); // Add delay for visibility
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            // Simulate click on Confirm button in deposit window
            Point depositConfirmButtonLocation = getRelativeLocation(depositFrame, "Confirm");
            robot.mouseMove(depositConfirmButtonLocation.x, depositConfirmButtonLocation.y);
            Thread.sleep(500); // Add delay for visibility
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            // Simulate click on Confirm button in withdraw window
            Point withdrawConfirmButtonLocation = getRelativeLocation(withdrawFrame, "Confirm");
            robot.mouseMove(withdrawConfirmButtonLocation.x, withdrawConfirmButtonLocation.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(3000); // Wait 3 seconds to get result
            JOptionPane.showMessageDialog(null, "The demo is done, you can check the result.\n \n" + "The current balance is " + Data.getInstance().getBalanceByAccountNum(account) , "Demo Ends", JOptionPane.INFORMATION_MESSAGE);
            Data.writeDataToLocalFile("data.txt");
            System.exit(0);
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unexpected ERROR" + Data.getInstance().getBalanceByAccountNum(account) , "Demo Ends", JOptionPane.WARNING_MESSAGE);
        }
    }


    private Point getRelativeLocation(JFrame frame, int amount) {
        String buttonText = "￡" + amount;
        return findButtonLocation(frame.getContentPane(), buttonText);
    }

    private Point getRelativeLocation(JFrame frame, String buttonText) {
        return findButtonLocation(frame.getContentPane(), buttonText);
    }

    private Point findButtonLocation(Container container, String buttonText) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(buttonText)) {
                    Rectangle bounds = button.getBounds();
                    Point locationOnScreen = button.getLocationOnScreen();
                    return new Point(locationOnScreen.x + bounds.width / 2, locationOnScreen.y + bounds.height / 2);
                }
            } else if (component instanceof Container) {
                Point location = findButtonLocation((Container) component, buttonText);
                if (location != null) {
                    return location;
                }
            }
        }
        return null; // Button not found
    }
}
