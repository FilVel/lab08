package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, INITIAL_AMOUNT);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(INITIAL_AMOUNT, this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
        assertEquals(this.mRossi, this.bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        assertEquals(0, this.bankAccount.getTransactionsCount());
        this.bankAccount.deposit(this.mRossi.getUserID(), INITIAL_AMOUNT);
        assertEquals(INITIAL_AMOUNT*2, this.bankAccount.getBalance());
        assertEquals(1, this.bankAccount.getTransactionsCount());
        this.bankAccount.chargeManagementFees(this.mRossi.getUserID());
        assertEquals(0, this.bankAccount.getTransactionsCount());
        assertEquals(INITIAL_AMOUNT*2-5.1, this.bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -INITIAL_AMOUNT);
            Assertions.fail("An impossible negative withdrawal has occurred");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), INITIAL_AMOUNT*10);
            Assertions.fail("A withdrawal of more money than present on the account has occurred");
        } catch (IllegalArgumentException e) {
            assertEquals("Insufficient balance", e.getMessage());
        }
    }
}
