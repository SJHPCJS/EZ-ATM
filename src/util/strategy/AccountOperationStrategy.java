/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package util.strategy;

import pojo.Account;

/**
 * Interface for defining operations that can be performed on accounts.
 */
public interface AccountOperationStrategy {

    /**
     * Deposits a specified amount of money into the specified account.
     *
     * @param accountNum The account number where money will be deposited
     * @param money The amount of money to deposit
     */
    void deposit(String accountNum, double money);

    /**
     * Withdraws a specified amount of money from the specified account.
     *
     * @param accountNum The account number from which money will be withdrawn
     * @param money The amount of money to withdraw
     */
    void withdraw(String accountNum, double money);

    /**
     * Transfers a specified amount of money from one account to another.
     *
     * @param from The account number from which money will be transferred
     * @param to The account number to which money will be transferred
     * @param money The amount of money to transfer
     */
    void transfer(String from, String to, double money);

    /**
     * Retrieves details of the specified account.
     *
     * @param accountNum The account number to inquire about
     * @return The Account object if found, otherwise null
     */
    Account inquire(String accountNum);
}
