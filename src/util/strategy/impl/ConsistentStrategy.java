/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package util.strategy.impl;

import pojo.Account;
import util.Data;
import util.strategy.AccountOperationStrategy;

/**
 * An implementation of AccountOperationStrategy that ensures thread-safe operations
 * on accounts by using synchronization.
 */
public class ConsistentStrategy implements AccountOperationStrategy {

    /**
     * Retrieves an account by its account number.
     *
     * @param accountNum The account number to search for
     * @return The Account object if found, otherwise null
     */
    private Account getAccountByAccountNum(String accountNum) {
        for (Account account : Data.getInstance().getAccounts()) {
            if (account.getAccountNum().equals(accountNum)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Deposits a specified amount of money into the account with the given account number.
     * This operation is thread-safe.
     *
     * @param accountNum The account number to deposit money into
     * @param money The amount of money to deposit
     */
    @Override
    public void deposit(String accountNum, double money) {
        Account account = getAccountByAccountNum(accountNum);
        if (account != null) {
            synchronized (this) {
                // Update balance within synchronized block
                account.setBalance(account.getBalance() + money);
            }
        }
    }

    /**
     * Withdraws a specified amount of money from the account with the given account number.
     * This operation is thread-safe.
     *
     * @param accountNum The account number to withdraw money from
     * @param money The amount of money to withdraw
     */
    @Override
    public void withdraw(String accountNum, double money) {
        Account account = getAccountByAccountNum(accountNum);
        if (account != null) {
            synchronized (this) {
                // Update balance within synchronized block
                account.setBalance(account.getBalance() - money);
            }
        }
    }

    /**
     * Transfers a specified amount of money from one account to another.
     * This operation is thread-safe.
     *
     * @param from The account number to transfer money from
     * @param to The account number to transfer money to
     * @param money The amount of money to transfer
     */
    @Override
    public void transfer(String from, String to, double money) {
        Account fromAccount = getAccountByAccountNum(from);
        Account toAccount = getAccountByAccountNum(to);
        if (fromAccount != null && toAccount != null) {
            synchronized (this) {
                // Update balances within synchronized block
                fromAccount.setBalance(fromAccount.getBalance() - money);
                toAccount.setBalance(toAccount.getBalance() + money);
            }
        }
    }

    /**
     * Inquires about the details of an account with the given account number.
     * This operation is thread-safe.
     *
     * @param accountNum The account number to inquire about
     * @return The Account object if found, otherwise null
     */
    @Override
    public Account inquire(String accountNum) {
        synchronized (this) {
            // Retrieve account details within synchronized block
            return getAccountByAccountNum(accountNum);
        }
    }
}
