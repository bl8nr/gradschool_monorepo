package com.cscie97.smartcity.ledger;

public class Account {
    private String address;
    private int balance;

    /**
     * Create new account, set its unique address
     * @param address String Unique identifier used to access the account
     */
    public Account(String address) {
        this.address = address;
    }

    /**
     * @return the current balance of the account
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * @param balance number amount representing the new balance in the account
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * @return the unique accountId used to address the account instance
     */
    public String getAddress() {
        return this.address;
    }

}
