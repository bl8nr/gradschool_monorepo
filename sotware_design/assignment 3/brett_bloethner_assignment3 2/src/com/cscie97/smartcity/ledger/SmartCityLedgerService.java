package com.cscie97.smartcity.ledger;
import com.cscie97.smartcity.model.SmartCityModelService;

import java.util.*;

public class SmartCityLedgerService {
    private static SmartCityLedgerService uniqueInstance = new SmartCityLedgerService("ledger", "a test ledger", "a test seed");

    private String name;
    private String description;
    private String seed;

    private Block genesisBlock;
    private Block currentBlock;
    private TreeMap<Integer, Block> blockMap;
    private Map<String, Account> accountMap;

    private SmartCityLedgerService (String name, String description, String seed) {
        this.name = name;
        this.description = description;
        this.seed = seed;
        this.blockMap = new TreeMap<>();
        this.accountMap = new HashMap<>();

        Account masterAccount = new Account("master");
        masterAccount.setBalance(2147483647);

        this.genesisBlock = new Block();
        this.currentBlock = this.genesisBlock;
        this.accountMap.put(masterAccount.getAddress(), masterAccount);

        System.out.println("SmartCityLedgerService Initialized");
    }

    public static SmartCityLedgerService getInstance() {
        return uniqueInstance;
    }

    /**
     * @return String The name of the ledger
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return String The description of the ledger
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return String The seed of the ledger
     */
    public String getSeed() {
        return this.seed;
    }

    /**
     * Create a new account with the unique identifier provided. Set the new accounts balance to 0 and return the new
     * accounts identifier/address after adding the new account to the ledgers account map
     * @param accountId String The unique identifier for a new Account. No other account must already have this ID.
     * @return String The accountId now associated with the new account
     * @throws LedgerException if an account with the same accountId already exists
     */
    public String createAccount(String accountId) throws LedgerException {

        if (this.accountMap.get(accountId) != null) {
            throw new LedgerException("Create Account", "Failed. Account with that ID already exists");
        }

        Account newAccount = new Account(accountId);
        newAccount.setBalance(0);
        this.accountMap.put(newAccount.getAddress(), newAccount);

        this.createTransaction("fundAccountFromMasterAndFillBlock1"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock2"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock3"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock4"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock5"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock6"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock7"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock8"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock9"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);
        this.createTransaction("fundAccountFromMasterAndFillBlock10"+accountId, 100, 10,"filler to commit account to blockchain", "master", accountId);

        return newAccount.getAddress();
    }

    /**
     * Create a transaction to be used in the processTransaction method.
     * @param id String The unique ID to be assigned to transaction
     * @param amount int The amount to be transferred from the payer to the receiver
     * @param fee int The fee amount to be transferred from the payer to master
     * @param note String A human readable note to help explain the transaction
     * @param payer String The unique identifier associated with the payers account
     * @param receiver String The unique identifier associated with the receivers account
     * @return Transaction A transaction object with the passed in data and Account references for payer and receiver
     * @throws LedgerException if either the payer or the receiver doest not exist in the ledger
     */
    public String createTransaction(String id, int amount, int fee, String note, String payer, String receiver) throws LedgerException{
        Account payerAccount = this.accountMap.get(payer);
        Account receiverAccount = this.accountMap.get(receiver);

        if (payerAccount == null || receiverAccount == null) {
            throw new LedgerException("Create Transaction", "Payer and/or receiver account does not exist");
        }

        Transaction transaction = new Transaction(id, amount, fee, note, payerAccount, receiverAccount);

        return this.processTransaction(transaction);
    }

    /**
     * Validate a transactions specs and add it to the blockchain. After its added, commit the block to the blockchain
     * if there are more than 10 transactions in the current blocks transaction map.
     * @param transaction A complete Transaction object
     * @return Strinig The Id of the newly added transaction
     * @throws LedgerException if the transactions fee does not meet the minimum requirements
     * @throws LedgerException if the transactions amount exceeds the maximum allows amount
     * @throws LedgerException if the transactions payer doesnt have enough funds to cover amount and fee
     */
    private String processTransaction(Transaction transaction) throws LedgerException{
        Account payer = transaction.getPayer();
        Account receiver = transaction.getReceiver();
        Account master = this.accountMap.get("master");

        if (transaction.getFee() < 10) {
            throw new LedgerException("Create Transaction", "Failed. Transaction fee does not meet the minimum requirements.");
        }

        if (transaction.getAmount() > 2147483647) {
            throw new LedgerException("Create Transaction", "Amount exceeds maximum transferrable amount");
        }

        if (payer.getBalance() < (transaction.getFee() + transaction.getAmount())) {
            throw new LedgerException("Process Transaction", "Failed. Not enough funds available for transaction");
        }

        // add the transaction to the current block
        this.currentBlock.addTransaction(transaction);

        // update the new balances of the payer, receiver and master
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());
        payer.setBalance(payer.getBalance() - (transaction.getAmount() + transaction.getFee()));
        master.setBalance(master.getBalance() + transaction.getFee());

        // commit the block to the blockchain if necessary
        this.checkBlockSize();

        return transaction.getTransactionId();
    }

    /**
     * Return the account balance for the provided accountId. The balance is read from the last committed block.
     * @param accountId String The unique identifier for the account whose balance is being fetched
     * @return int The balance of coin available to the accountId provided
     * @throws LedgerException if there is no previous block, its then implied that there cannot be any accounts
     * @throws LedgerException if the account id does not exist as a key in the previous blocks account balance map
     */
    public int getAccountBalance(String accountId) throws LedgerException {
        Block previousBlock = this.blockMap.get(this.currentBlock.getBlockNumber() - 1);
        if (previousBlock == null) {
            throw new LedgerException("Get Account Balance", "Failed. Account could not be found in blockchain");
        }

        boolean accountExists = previousBlock.getAccountBalanceMap().containsKey(accountId);
        if (accountExists == false) {
            throw new LedgerException("Get Account Balance", "Failed. Account could not be found in blockchain");
        }

        return previousBlock.getAccountBalanceMap().get(accountId);
    }

    /**
     * Check the size of the current blocks transaction map, if the size is 10 then generate the hash for the block,
     * copy the account balances into the block in the blocks accountBalance map, commit the block to the blockchain
     * and set the ledgers currentBlock to a new Block generated from the previous current block
     */
    private void checkBlockSize() {
        int remainingTransactions = 10 - this.currentBlock.getTransactionList().size();
        if (remainingTransactions == 0) {
            this.accountMap.forEach((k, v) -> {
                this.currentBlock.addAccountBalance(v);
            });
            this.currentBlock.generateHash(this.seed);
            this.blockMap.put(this.currentBlock.getBlockNumber(), currentBlock);
            this.currentBlock = new Block(this.currentBlock);
        }
    }

    /**
     * Return a map of all the latest committed accounts and associated balances (in the most recent committed block)
     * @return Map<String, Integer> A map listing all the latest accounts and balances in the blockchain
     * @throws LedgerException if there is no committed block (ie the genesis block is still in work)
     */
    public Map<String, Integer> getAccountBalances() throws LedgerException {
        int currentBlockNumber = this.currentBlock.getBlockNumber();

        if (currentBlockNumber == 1) {
            throw new LedgerException("Get Account Balances", "Failed. No accounts could be found in blockchain");
        }

        return this.blockMap.get(currentBlockNumber - 1).getAccountBalanceMap();
    }

    /**
     * Return the block associated with the provided block number
     * @param blockNumber int The unique number associated the the block being requested
     * @return Block the block being requested
     */
    public Block getBlock(int blockNumber) {
        return this.blockMap.get(blockNumber);
    }

    /**
     * Get a transaction by the transactions unique id. Iterate through each committed block to find the transaction,
     * then return the transaction once its found
     * @param transactionId String The unique identifier by the the transaction can be found
     * @return Transaction The transaction associated with the transactionId
     */
    public Transaction getTransaction(String transactionId) throws LedgerException {
        final Transaction[] transaction = new Transaction[1];

        // for each block in the blockchain...
        this.blockMap.forEach((k, v) -> {
            // ...see if the transaction exists by id in the blocks transactionList...
            Transaction transactionListGetResult = v.getTransactionList().get(transactionId);
            // ...if it does exist then set it as transaction[0], which will be returned
            if (v.getTransactionList().get(transactionId) != null) {
                transaction[0] = transactionListGetResult;
            }
        });

        if (transaction[0] == null) {
            throw new LedgerException("Get Transaction", "Failed. Transaction does not exist.");
        }

        return transaction[0];
    }


    /**
     * Validate that each block in the blockchain has a valid previousHash, contains no more than 10 transactions and
     * has an account balance map that accounts for all the initial value of the blockchain
     * @throws LedgerException if a block has an invalid previousHash
     * @throws LedgerException if a block contains more than 10 transactions
     * @throws LedgerException if a blocks total value in the accountBalance map does not equal 2147483647
     */
    public void validate() throws LedgerException {
        // isolate each block to be validated individually, starting with the last block and counting down
        int blockCount = this.blockMap.size();
        while (blockCount > 0) {
            Block block = this.blockMap.get(blockCount);

            // validate that the previousHash parameter matches the hash of the previous block, unless genesis block
            if (block.getBlockNumber() > 1) {
                Block previousBlock = this.blockMap.get(block.getBlockNumber() - 1);
                if (block.getPreviousHash() != previousBlock.getHash()) {
                    throw new LedgerException("Validate", "Validation of a previousHash failed.");
                }
            }

            // validate that there are only 10 transactions in the block
            if (block.getTransactionList().size() > 10) {
                throw new LedgerException("Validate", "Failed. One or more blocks contained more than 10 transactions");
            }

            // calculate the total value of the blockchain according to the blocks accountBalance map
            int totalAccountBalance = 0;
            Iterator accountBalanceIterator = block.getAccountBalanceMap().entrySet().iterator();
            while (accountBalanceIterator.hasNext()) {
                Map.Entry accountBalance = (Map.Entry) accountBalanceIterator.next();
                totalAccountBalance = totalAccountBalance + Integer.parseInt(accountBalance.getValue().toString());
            }

            // validate that the total value of the blockchain is 2147483647
            if (totalAccountBalance != 2147483647) {
                throw new LedgerException("Validate", "Validation of total blockchain value failed one or more times");
            }

            // de-increment block count so the next previous block can be validated
            --blockCount;
        }
    }

}
