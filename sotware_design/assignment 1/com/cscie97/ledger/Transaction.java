package com.cscie97.ledger;

public class Transaction {
    private String transactionId;
    private int amount;
    private int fee;
    private String note;

    private Account payer;
    private Account receiver;

    /**
     * Construct the Transaction object, fill in the necessary components from data passed in as args
     *
     * @param id       String The unique identifier used to find/access the transaction
     * @param amount   int The amount of coin transferred for this transaction
     * @param fee      int The fee amount used for the transaction
     * @param note     String A note used to store human readable details for the transaction
     * @param payer    Account The account reference for the payer for the transaction
     * @param receiver Account The account reference for the receiver for the transaction
     */
    public Transaction(String id, int amount, int fee, String note, Account payer, Account receiver) {
        this.transactionId = id;
        this.note = note;
        this.payer = payer;
        this.receiver = receiver;
        this.amount = amount;
        this.fee = fee;
    }

    /**
     * @return int The amount of coin transferred for this transaction
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * @return int The fee amount used for the transaction
     */
    public int getFee() {
        return this.fee;
    }

    /**
     * @return Account The account reference for the payer for the transaction
     */
    public Account getPayer() {
        return this.payer;
    }

    /**
     * @return Account The account reference for the receiver for the transaction
     */
    public Account getReceiver() {
        return this.receiver;
    }

    /**
     * @return String The unique transactionId for the transaction
     */
    public String getTransactionId() {
        return this.transactionId;
    }

    /**
     * Calculate and return the hash for the transaction
     *
     * @return String A unique hash based on the transactions contents
     */
    public String toHash() {
        return Util.generateSha256HashFromString(this.toString());
    }

    /**
     * Override the toString method to make it more intelligible when printed out
     * @return String An easily readble string summary of the transaction object
     */
    public String toString() {
        return String.format("ID: %s, %s from %s to %s, Fee: %s, Note: %s",
                this.getTransactionId(),
                this.amount,
                this.payer.getAddress(),
                this.receiver.getAddress(),
                this.fee,
                this.note);
    }
}