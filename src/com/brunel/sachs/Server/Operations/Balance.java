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
        MessageHandler.sendMessage(Thread.currentThread() + " Account " + accountNumber
                + " balance: " + account.getBalance(accountNumber) + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds");
        this.releaseReadLock(accountNumber);
        choices();
    }

    private final int accountNumber;

}
