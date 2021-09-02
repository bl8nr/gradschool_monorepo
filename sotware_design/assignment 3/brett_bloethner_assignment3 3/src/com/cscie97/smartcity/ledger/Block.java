package com.cscie97.smartcity.ledger;

import java.util.*;

public class Block {
    private int blockNumber;
    private String previousHash;
    private String hash;

    private Map<String, Transaction> transactionList;
    private Map<String, Integer> accountBalanceMap;
    private Block previousBlock;

    /**
     * Construct a block. This is meant to be used to construct a block only when a previous block is not available.
     * Its used to construct the genesis block, so the blockNumber is automatically assigned as 1. Also initialize the
     * accountBalanceMap and transactionList for use later
     */
    public Block() {
        this.blockNumber = 1;
        this.accountBalanceMap = new HashMap<>();
        this.transactionList = new LinkedHashMap<>(); // preserve order of insertion
    }

    /**
     * Construct a block from a previous block. This is meant to be used to construct every block after the genesis
     * block. A previousBlock reference the block that came before it is set, as well as the previous blocks hash. The
     * new block number is calculated based on the previous blocks blockNumber. Also initialize the accountBalanceMap
     * and transactionList for use later
     * @param previousBlock Block The block that was in-work and completed immediately before this new block
     */
    public Block(Block previousBlock) {
        this.previousBlock = previousBlock;
        this.blockNumber = this.previousBlock.getBlockNumber() + 1;
        this.previousHash = this.previousBlock.getHash();
        this.accountBalanceMap = new HashMap<>();
        this.transactionList = new LinkedHashMap<>(); // preserve order of insertion
    }

    /**
     * @return int The automatically generated sequential blockNumber associated with this block
     */
    public int getBlockNumber() {
        return this.blockNumber;
    }

    /**
     * @return String The unique hash for this block, constructed from the transaction merkle tree and other block data
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * @return String The unique hash associated with the block prior to this one
     */
    public String getPreviousHash() { return this.previousHash; }

    /**
     * @return Map<String, Integer> a map of all of the account ids/balances at the time this block was completed
     */
    public Map<String, Integer> getAccountBalanceMap() {
        return this.accountBalanceMap;
    }

    /**
     * Add account balance information to the accountBalance map
     * @param account Account The account whose ID and balance will be included in this blocks set of account balances
     */
    public void addAccountBalance(Account account) {
        this.accountBalanceMap.put(account.getAddress(), account.getBalance());
    }

    /**
     * @return Map<String, Transaction> a map of references for all the Transactions included in this block
     */
    public Map<String, Transaction> getTransactionList() {
        return this.transactionList;
    }

    /**
     * Add a transaction reference to the transactionList map
     * @param transaction Transaction The transaction object to be included as one of the 10 transactions in this block
     */
    public void addTransaction(Transaction transaction) {
        this.transactionList.put(transaction.getTransactionId(), transaction);
    }

    /**
     * Generate the Merkle root using recursion
     * @param hashes String[] The bottom most layer of hashes use to compute the Merkle tree
     * @return String[1] The top most layer (root) of the hash(s) of the computed Merkle tree
     */
    private String[] getMerkleRoot(String[] hashes) {
        // create a string array fixed to the length of the next highest layer (parent layer)
        int lengthOfSample = hashes.length;
        String[] result = new String[(lengthOfSample / 2) + (hashes.length % 2)];

        // for each position in the parent/next/result layer...
        int i = 0;
        while(i < result.length) {

            // if the position corresponds to a solo last hash in the child layer...
            if ((i * 2) == (lengthOfSample - 1)) {
                // ...store that same hash from the child layer into the parent layer (for odd number of hashes)
                result[i] = hashes[i * 2];
            } else {
                // ...else compute the new hash for the position using the two child hashes from the below layer
                String hashStringConcat = String.format("%s %s", hashes[i * 2], hashes[(i * 2) + 1]);
                result[i] = Util.generateSha256HashFromString(hashStringConcat);
            }
            ++i;
        };

        // if there is more than one result in the newly created layer...
        if (result.length > 1) {
            // ...then get the next layer of hashes using the newly created layer
            return getMerkleRoot(result);
        } else {
            // ...else return the result (the result is the Merkle root)
            return result;
        }
    }

    /**
     * Generate the hash for this block by calculating the Merkle root from the transactions and combining that with
     * other unique block data and the ledger seed, then combing it all together and hashing it
     * @param seed String The ledger seed created when the ledger was created
     * @return String The unique hash assuring validity of the block transactions, previous block and account balances
     */
    public String generateHash(String seed) {
        List<String> originalHashes = new ArrayList<String>();
        String[] level1 = new String[10];

        // generate the first bottom most level of merkle tree hashes
        this.transactionList.forEach((id, transaction) -> {
            originalHashes.add(transaction.toHash());
        });

        // arrange the first level of hashes into a more iterable String array and get the merkle root
        for(int i = 0; i < level1.length; ++i) {
            level1[i] = originalHashes.toArray()[i].toString();
        }
        String merkleRoot = this.getMerkleRoot(level1)[0];

        // construct the block hash using the blocks data and the merkle root
        String stringToHash = String.format("%s-%s-%s-%s-%s-%s", this.blockNumber, merkleRoot, this.accountBalanceMap, this.transactionList, this.previousHash, seed);
        this.hash = Util.generateSha256HashFromString(stringToHash);

        return this.hash;
    }
}
