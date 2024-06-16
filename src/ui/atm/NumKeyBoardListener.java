/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package ui.atm;

/**
 * Listener interface for handling events from a numeric keypad.
 */
public interface NumKeyBoardListener {

    /**
     * Called when the "Confirm" button is pressed on the numeric keypad.
     *
     * @param value         The current value entered on the keypad.
     * @param shouldSwitch  Indicates whether to switch to another view after confirmation.
     */
    void onConfirm(String value, boolean shouldSwitch);

    /**
     * Called whenever there is an input event (button press) on the numeric keypad.
     *
     * @param value  The current value entered on the keypad.
     */
    void onInput(String value);
}
