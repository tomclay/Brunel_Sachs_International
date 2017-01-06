package com.brunel.sachs.Server.Operations;

import com.brunel.sachs.Server.MessageHandler;
import com.brunel.sachs.Server.Transaction;

/**
 * Created by Tom Clay ESQ.
 */

public class Withdraw extends Transaction {

    private final int accountNumber;
    private final float amount;

    /**
     * A thread to make a withdrawal from the specified account
     *
     * @param accountNumber index of the account
     * @param amount        value to be withdrawn
     */
    public Withdraw(int accountNumber, float amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Override
    public void run() {
        this.delay();
        this.acquireWriteLock(accountNumber);
        float newBalance = account.withdraw(accountNumber, amount);
        MessageHandler.sendMessage(Thread.currentThread() + " withdrew " + amount + " in account "
                + accountNumber + ". New Balance: " + newBalance + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds");
        this.releaseWriteLock(accountNumber);
        choices();
    }

}