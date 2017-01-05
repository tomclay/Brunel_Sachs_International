package com.brunel.sachs.Server.Operations;

import com.brunel.sachs.Server.Transaction;

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */

public class Withdraw extends Transaction {

    /**
     * A thread to make a withdrawal from the specified account
     * @param accountNumber index of the account
     * @param amount value to be withdrawn
     */
    public Withdraw(int accountNumber, int amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Override
    public void run(){
        this.delay();
        this.acquireWriteLock(accountNumber);
        float newBalance=  account.withdraw(accountNumber, amount);
        System.out.println(Thread.currentThread() + " withdrew " + amount + " from account "
                + accountNumber + ". New Balance: " + newBalance);
        this.releaseWriteLock(accountNumber);
    }

    private final int accountNumber;
    private final int amount;

}