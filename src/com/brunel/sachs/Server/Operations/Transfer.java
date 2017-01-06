package com.brunel.sachs.Server.Operations;

import com.brunel.sachs.Server.MessageHandler;
import com.brunel.sachs.Server.Transaction;

/**
 * Created by Tom Clay ESQ.
 */

public class Transfer extends Transaction {

    private final int accountFrom;
    private final int accountTo;
    private final float amount;

    /**
     * A thread to transfer funds from one account to another
     *
     * @param accountFrom index of the account from which funds will be
     *                    transferred
     * @param accountTo   index of the account to which funds will be transferred
     * @param amount      value to be transferred
     */

    public Transfer(int accountFrom, int accountTo, float amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    @Override
    public void run() {
        this.delay();
        // Multiple write locks are required as two accounts are being accessed
        this.acquireWriteLock(Math.min(accountFrom, accountTo));
        this.acquireWriteLock(Math.max(accountFrom, accountTo));
        float dest_account_balance = account.transfer(accountFrom, accountTo, amount);
        MessageHandler.sendMessage("Transfer complete. The recipients balance is now " + dest_account_balance + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds");
        this.releaseWriteLock(Math.min(accountFrom, accountTo));
        this.releaseWriteLock(Math.max(accountFrom, accountTo));
        choices();
    }
}