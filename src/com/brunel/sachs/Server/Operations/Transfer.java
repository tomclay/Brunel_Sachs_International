package com.brunel.sachs.Server.Operations;

import com.brunel.sachs.Server.Transaction;

import java.util.Random;

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */
public class Transfer extends Transaction {

    /**
     * A thread to transfer funds from one account to another
     *
     * @param accountFrom index of the account from which funds will be
     * transferred
     * @param accountTo index of the account to which funds will be transferred
     * @param amount value to be transferred
     */
    public Transfer(int accountFrom, int accountTo, float amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    @Override
    public void run() {
        this.delay();
        this.acquireWriteLock(Math.min(accountFrom, accountTo));
        this.acquireWriteLock(Math.max(accountFrom, accountTo));
        System.out.println(account.transfer(accountFrom, accountTo, amount));
        this.releaseWriteLock(Math.min(accountFrom, accountTo));
        this.releaseWriteLock(Math.max(accountFrom, accountTo));
        choices();
    }

    private final int accountFrom;
    private final int accountTo;
    private final float amount;
    private final Random r = new Random();
}