package com.brunel.sachs.Server.Operations;

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
    public Deposit(int accountNumber, int amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Override
    public void run(){
        this.delay();
        this.acquireWriteLock(accountNumber);
        float newBalance = account.deposit(accountNumber, amount);
        System.out.println(Thread.currentThread() + " deposited " + amount + " in account "
                + accountNumber + ". New Balance: " + newBalance);
        this.releaseWriteLock(accountNumber);
    }

    private final int accountNumber;
    private final int amount;

}