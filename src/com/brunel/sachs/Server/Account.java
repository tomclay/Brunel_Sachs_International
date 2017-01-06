package com.brunel.sachs.Server;

import static com.brunel.sachs.Server.Database.get_bal;


/**
 * Created by Tom Clay ESQ.
 */

public class Account {

    /**
     * This class comprises of the account operations that are shared between the clients.
     * As all clients are contending for access, two-phase locking is used to prevent data
     * inconsistencies.
     *
     * There the additions, subtractions, and transfers are built. The new balances are sent
     * to the Database.
     */

    // Read and write locks
    private static final boolean[] writeLock = new boolean[10];
    private static final int[] readLocks = new int[10];
    private static final boolean[] writeLockWaiting = new boolean[10];


    /**
     * Acquires a read lock for a particular account if that account does not have
     * a current write lock. The read lock will allow other read locks, but not
     * write locks, to be established
     *
     * @param i index of the account for which a lock is requested
     * @return true if the lock is successfully acquired, false otherwise
     */

    public static boolean acquireReadLock(int i) {
        if (!writeLock[i] && !writeLockWaiting[i]) {
            readLocks[i]++;
            return true;
        }
        return false;
    }

    /**
     * Acquires a write lock for a particular account if no other locks (read or
     * write) are currently in place. No other locks can be established after a
     * write lock is in place
     *
     * @param i index of the account for which a lock is requested
     * @return true if the lock is successfully acquired, false otherwise
     */

    public static boolean acquireWriteLock(int i) {
        if (readLocks[i] == 0 && !writeLock[i]) {
            writeLock[i] = true;
            writeLockWaiting[i] = false;
            return true;
        }
        writeLockWaiting[i] = true;
        return false;
    }

    /**
     * Releases one read lock from the specified account
     *
     * @param i index of the account to be released
     */

    public static void releaseReadLock(int i) {
        readLocks[i]--;
    }

    /**
     * Removes the write lock from the specified account
     *
     * @param i index of the account to be released
     */

    public static void releaseWriteLock(int i) {
        writeLock[i] = false;
    }

    /**
     * A function for reading the balance of an account with index i
     *
     * @param i the index of the desired account
     * @return the balance of the requested account
     */

    public static float getBalance(int i) {
        return get_bal(i);
    }

    /**
     * Deposits the specified amount into account i
     *
     * @param i the index of the account to be modified
     * @param amount the amount to be added
     */

    public static float deposit(int i, float amount) {
        float oldBalance = Database.get_bal(i);
        float newBalance = oldBalance + amount;
        Database.update_bal(i, newBalance);
        return newBalance;
    }

    /**
     * Withdraws the specified amount from account i
     *
     * @param i the index of the account to be modified
     * @param amount the amount to be withdrawn
     */

    public static float withdraw(int i, float amount) {
        float oldBalance = Database.get_bal(i);
        float newBalance = oldBalance - amount;
        Database.update_bal(i, newBalance);
        return newBalance;
    }

    /**
     * Transfers the specified amount from account i to account y
     *
     * @param i the index of the originating account
     * @param y the index of the destination account
     * @param amount the amount to be transferred
     */

    public static float transfer(int i, int y, float amount) {
        float dest_account_new_bal = 0;
        withdraw(i, amount);
        dest_account_new_bal = deposit(y, amount);
        return dest_account_new_bal;
    }
}