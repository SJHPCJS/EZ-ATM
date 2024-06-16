/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package ui.atm;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for setting up JFrame attributes and locations.
 */
public class UIUtil {

    /**
     * Sets the location of the main JFrame to the center of the screen horizontally
     * and 1/30th down from the top of the screen vertically.
     *
     * @param f The JFrame to set the location for.
     */
    public static void setMainFrameLocation(JFrame f) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = f.getWidth();

        f.setLocation(screenWidth / 2 - frameWidth / 2, screenHeight / 30);
    }

    /**
     * Sets the size, layout, and title of the main JFrame.
     *
     * @param f The JFrame to set attributes for.
     */
    public static void setMainFrameAttribute(JFrame f) {
        f.setSize(720, 500);
        f.setLayout(new BorderLayout());

        JLabel title = new JLabel("EZ ATM");
        title.setPreferredSize(new Dimension(720, 100));
        title.setOpaque(true);
        title.setBackground(Color.getHSBColor(0.568F, 0.314F, 0.949F));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 40));
        f.add(title, BorderLayout.NORTH);

        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Sets the location of the JFrame based on the specified type.
     *
     * @param f    The JFrame to set the location for.
     * @param type The type of location ("left" or "right").
     */
    public static void setTestFrameLocation(JFrame f, String type) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        switch (type) {
            case "left" -> f.setLocation(screenSize.width / 40, screenSize.height / 4);
            case "right" -> f.setLocation(10 * screenSize.width / 20, screenSize.height / 4);
        }
    }
}
