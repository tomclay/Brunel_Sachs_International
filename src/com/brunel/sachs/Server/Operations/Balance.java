package com.brunel.sachs.Server.Operations;

import com.brunel.sachs.Server.MessageHandler;
import com.brunel.sachs.Server.Transaction;

/**
 * Created by Tom Clay ESQ.
 */

public class Balance extends Transaction {

    private final int accountNumber;

    /**
     * A thread for retrieving the balance of the account specified.
     *
     * @param accountNumber ID of the account
     */

    public Balance(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void run() {
        // Delay the transaction by a random amount of time to avoid thread deadlock
        this.delay();
        // Acquire a read-only lock for this transaction
        this.acquireReadLock(accountNumber);
        // Do transaction
        MessageHandler.sendMessage(Thread.currentThread() + " Account " + accountNumber
                + " balance: " + account.getBalance(accountNumber) + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds");
        // Release lock
        this.releaseReadLock(accountNumber);
        // Ask the user what to do next
        choices();
    }

}
