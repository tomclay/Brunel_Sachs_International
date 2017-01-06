package com.brunel.sachs.Server.Operations;

import com.brunel.sachs.Server.MessageHandler;
import com.brunel.sachs.Server.Transaction;

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */
public class Deposit extends Transaction {

    /**
     * A thread to deposit money into an account
     * @param accountNumber index of the account
     * @param amount value to be deposited in the account
     */
    public Deposit(int accountNumber, float amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Override
    public void run(){
        this.delay();
        this.acquireWriteLock(accountNumber);
        float newBalance = account.deposit(accountNumber, amount);
        MessageHandler.sendMessage(Thread.currentThread() + " deposited " + amount + " in account "
                + accountNumber + ". New Balance: " + newBalance + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds");
        this.releaseWriteLock(accountNumber);
        choices();
    }

    private final int accountNumber;
    private final float amount;

}