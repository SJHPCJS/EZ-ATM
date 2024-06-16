/**
 * @author Group10: Qi Xiao, Jiashuo Chang and Yaxin Huang
 * @version 1.0
 * @date 2024/6/16
 */
package util;

import pojo.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing accounts data.
 */
public class Data {
    private List<Account> accounts;
    private static Data data = readDataFromLocalFile("data.txt");

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private Data() {}

    /**
     * Retrieves the singleton instance of Data.
     *
     * @return The singleton instance of Data.
     */
    public static Data getInstance() {
        return data;
    }
    public static void refresh() {data = readDataFromLocalFile("data.txt");}
    /**
     * Reads account data from a local file and initializes Data object.
     *
     * @param path The path to the data file.
     * @return The Data object populated with account data from the file.
     */
    public static Data readDataFromLocalFile(String path) {
        Data ret = new Data();
        ret.accounts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    String accountNum = parts[0];
                    String pin = parts[1];
                    String username = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    Account account = new Account(accountNum, pin, username, balance);
                    ret.getAccounts().add(account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Writes account data to a local file.
     *
     * @param path The path to the file where data will be written.
     */
    public static void writeDataToLocalFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Account account : getInstance().getAccounts()) {
                bw.write(account.getAccountNum() + " " +
                        account.getPIN() + " " +
                        account.getUsername() + " " +
                        account.getBalance());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the list of accounts managed by Data.
     *
     * @return The list of accounts.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Retrieves the balance of an account based on its account number.
     *
     * @param accountNum The account number of the account.
     * @return The balance of the account, or 0.0 if not found.
     */
    public double getBalanceByAccountNum(String accountNum) {
        for (Account account : Data.getInstance().getAccounts()) {
            if (account.getAccountNum().equals(accountNum)) {
                return account.getBalance();
            }
        }
        return 0.0;
    }
}
