package com.brunel.sachs.Server.Operations;

import com.brunel.sachs.Server.Account;
import com.brunel.sachs.Server.MessageHandler;
import com.brunel.sachs.Server.Transaction;

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */


public class Balance extends Transaction {

    /**
     * A thread for retrieving the balance of the account specified
     * @param accountNumber index of the account
     */
    public Balance(int accountNumber) {
        this.accountNumber = accountNumber;
    }


    @Override
    public void run(){
        this.delay();
        this.acquireReadLock(accountNumber);
        System.out.println(Thread.currentThread() + " Account " + accountNumber
                + " balance: " + account.getBalance(accountNumber));
        this.releaseReadLock(accountNumber);
    }

    private final int accountNumber;

}
