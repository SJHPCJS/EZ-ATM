/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package pojo;

/**
 * Represents an Account object with attributes such as account number, PIN, username, and balance.
 */
public class Account {
    private String accountNum; // Account number associated with the account
    private String PIN; // PIN (Personal Identification Number) for security
    private String username; // Username of the account holder
    private double balance; // Current balance of the account

    /**
     * Constructor for creating an Account object with account number and PIN.
     *
     * @param accountNum The account number associated with the account.
     * @param PIN The PIN (Personal Identification Number) for accessing the account.
     */
    public Account(String accountNum, String PIN) {
        this.accountNum = accountNum;
        this.PIN = PIN;
    }

    /**
     * Constructor for creating a complete Account object with all attributes.
     *
     * @param accountNum The account number associated with the account.
     * @param PIN The PIN (Personal Identification Number) for accessing the account.
     * @param username The username of the account holder.
     * @param balance The current balance of the account.
     */
    public Account(String accountNum, String PIN, String username, double balance) {
        this.accountNum = accountNum;
        this.PIN = PIN;
        this.username = username;
        this.balance = balance;
    }

    /**
     * Retrieves the account number associated with the account.
     *
     * @return The account number.
     */
    public String getAccountNum() {
        return accountNum;
    }

    /**
     * Retrieves the PIN (Personal Identification Number) associated with the account.
     *
     * @return The PIN.
     */
    public String getPIN() {
        return PIN;
    }

    /**
     * Retrieves the username of the account holder.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the account number associated with the account.
     *
     * @param accountNum The new account number to set.
     */
    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    /**
     * Sets the username of the account holder.
     *
     * @param username The new username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the current balance of the account.
     *
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the current balance of the account.
     *
     * @param balance The new balance to set.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Sets the PIN (Personal Identification Number) associated with the account.
     *
     * @param PIN The new PIN to set.
     */
    public void setPIN(String PIN) {
        this.PIN = PIN;
    }
}
