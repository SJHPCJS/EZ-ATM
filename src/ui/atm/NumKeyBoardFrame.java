/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package ui.atm;

import javax.swing.*;
import java.awt.*;

/**
 * Frame class representing a numeric keypad for ATM operations.
 */
public class NumKeyBoardFrame extends JFrame {
    private String value;
    private Button[] buttons;
    private NumKeyBoardListener listener;

    /**
     * Constructs a NumKeyBoardFrame instance.
     *
     * @param relativeTo The component relative to which the frame should be positioned.
     * @param listener   The listener to handle keypad inputs.
     */
    public NumKeyBoardFrame(Component relativeTo, NumKeyBoardListener listener) {
        this.listener = listener;
        initialize(relativeTo);
        setVisible(true);
    }

    /**
     * Initializes the NumKeyBoardFrame with necessary settings and components.
     *
     * @param relativeTo The component relative to which the frame should be positioned.
     */
    private void initialize(Component relativeTo) {
        value = "";// Initialize the value to an empty string
        buttons = new Button[]{
                new Button("1"), new Button("2"), new Button("3"), new Button("Backspace"),
                new Button("4"), new Button("5"), new Button("6"), new Button("Reset"),
                new Button("7"), new Button("8"), new Button("9"), new Button(""),
                new Button("00"), new Button("0"), new Button("."), new Button("Confirm")};

        setUndecorated(true); // Remove window decorations
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Dispose the frame on close
        setSize(300, 300); // Set the size of the frame
        Point location = relativeTo.getLocation();
        int x = location.x + relativeTo.getWidth() / 2 - 150;
        int y = location.y + relativeTo.getHeight();
        setLocation(x, y); // Position the frame relative to the given component

        setLayout(new FlowLayout(FlowLayout.CENTER, 24, 28)); // Set layout with spacing

        // Add action listeners and customize button sizes
        for (int i = 0; i < buttons.length; i++) {
            if (i % 4 == 3) { // Handle special buttons (Backspace, Reset, Confirm)
                buttons[i].setPreferredSize(new Dimension(70, 40));
                switch (i) {
                    case 3 -> buttons[i].addActionListener((e) -> {
                        if (!value.isEmpty()) {
                            value = value.substring(0, value.length() - 1);// Remove last character
                        }
                        listener.onInput(value);// Notify listener of input change
                    });
                    case 7 -> buttons[i].addActionListener((e) -> {
                        value = "";//Reset the value
                        listener.onInput(value);// Notify listener of input change
                    });
                    case 15 -> buttons[i].addActionListener((e) -> {
                        if (listener != null) {
                            listener.onConfirm(this.value, true);// Notify listener of confirmation
                        }
                    });
                }
            } else { // Regular numeric and decimal buttons
                buttons[i].setPreferredSize(new Dimension(40, 40));
                buttons[i].addActionListener((e) -> {
                    this.value += e.getActionCommand();// Append button value to input
                    listener.onInput(value);// Notify listener of input change
                });
            }
            add(buttons[i]);// Add button to the frame
        }
    }
}
