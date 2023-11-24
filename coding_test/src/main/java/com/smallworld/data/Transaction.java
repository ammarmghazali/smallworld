package com.smallworld.data;

import java.util.List;

/**
 * Represents a financial transaction with associated information such as amount, sender, beneficiary, and compliance issues.
 */
public class Transaction {
    private String mtn;
    private double amount;
    private String senderFullName;
    private int senderAge;
    private String beneficiaryFullName;
    private int beneficiaryAge;
    private List<TransactionIssue> issues; // List to handle multiple issues

    /**
     * Constructs a new Transaction object with the specified details.
     *
     * @param mtn                 Unique identifier of the transaction.
     * @param amount              The amount of the transaction.
     * @param senderFullName      The full name of the sender initiating the transaction.
     * @param senderAge           The age of the sender.
     * @param beneficiaryFullName The name of the beneficiary receiving the transaction.
     * @param beneficiaryAge      The age of the beneficiary.
     * @param issues              List of issues associated with the transaction.
     */
    public Transaction(String mtn, double amount, String senderFullName, int senderAge,
                       String beneficiaryFullName, int beneficiaryAge, List<TransactionIssue> issues) {
        this.mtn = mtn;
        this.amount = amount;
        this.senderFullName = senderFullName;
        this.senderAge = senderAge;
        this.beneficiaryFullName = beneficiaryFullName;
        this.beneficiaryAge = beneficiaryAge;
        this.issues = issues;
    }

    /**
     * Gets the unique identifier of the transaction.
     *
     * @return The unique identifier (mtn) of the transaction.
     */
    public String getMtn() {
        return mtn;
    }

    /**
     * Gets the amount of the transaction.
     *
     * @return The amount of the transaction.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the full name of the sender initiating the transaction.
     *
     * @return The full name of the sender.
     */
    public String getSenderFullName() {
        return senderFullName;
    }

    /**
     * Gets the age of the sender.
     *
     * @return The age of the sender.
     */
    public int getSenderAge() {
        return senderAge;
    }

    /**
     * Gets the name of the beneficiary receiving the transaction.
     *
     * @return The name of the beneficiary.
     */
    public String getBeneficiaryFullName() {
        return beneficiaryFullName;
    }

    /**
     * Gets the age of the beneficiary.
     *
     * @return The age of the beneficiary.
     */
    public int getBeneficiaryAge() {
        return beneficiaryAge;
    }

    /**
     * Gets the list of issues associated with the transaction.
     *
     * @return List of issues associated with the transaction.
     */
    public List<TransactionIssue> getIssues() {
        return issues;
    }

    /**
     * Represents a compliance issue associated with a transaction.
     */
    public static class TransactionIssue {
        private Integer issueId;
        private boolean issueSolved;
        private String issueMessage;

        /**
         * Constructs a new TransactionIssue object with the specified details.
         *
         * @param issueId       The ID associated with the compliance issue.
         * @param issueSolved   Indicates whether the issue has been solved.
         * @param issueMessage  The message describing the compliance issue.
         */
        public TransactionIssue(Integer issueId, boolean issueSolved, String issueMessage) {
            this.issueId = issueId;
            this.issueSolved = issueSolved;
            this.issueMessage = issueMessage;
        }

        /**
         * Gets the ID associated with the compliance issue.
         *
         * @return The ID of the compliance issue.
         */
        public Integer getIssueId() {
            return issueId;
        }

        /**
         * Checks if the compliance issue has been solved.
         *
         * @return true if the issue is solved, false otherwise.
         */
        public boolean isIssueSolved() {
            return issueSolved;
        }

        /**
         * Gets the message describing the compliance issue.
         *
         * @return The message describing the compliance issue.
         */
        public String getIssueMessage() {
            return issueMessage;
        }
    }
}
