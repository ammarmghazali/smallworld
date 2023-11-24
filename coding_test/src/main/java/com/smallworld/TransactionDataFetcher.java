package com.smallworld;

import com.smallworld.data.Transaction;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A utility class for fetching and analyzing transaction data.
 */
public class TransactionDataFetcher {

    private List<Transaction> transactions; // Assuming you have a list of transactions

    /**
     * Constructs a TransactionDataFetcher with a list of transactions.
     *
     * @param transactions The list of transactions to be processed.
     */
    public TransactionDataFetcher(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Returns the sum of the amounts of all transactions.
     *
     * @return The total transaction amount.
     */
    public double getTotalTransactionAmount() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client.
     *
     * @param senderFullName The full name of the sender.
     * @return The total transaction amount sent by the specified client.
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        return transactions.stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the highest transaction amount.
     *
     * @return The maximum transaction amount.
     */
    public double getMaxTransactionAmount() {
        return transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
    }

    /**
     * Counts the number of unique clients that sent or received a transaction.
     *
     * @return The number of unique clients.
     */
    public long countUniqueClients() {
        Set<String> uniqueClients = new HashSet<>();
        for (Transaction transaction : transactions) {
            uniqueClients.add(transaction.getSenderFullName());
            uniqueClients.add(transaction.getBeneficiaryName());
        }
        return uniqueClients.size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved.
     *
     * @param clientFullName The full name of the client.
     * @return True if the client has an open compliance issue, false otherwise.
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        for (Transaction transaction : transactions) {
            if ((transaction.getSenderFullName().equals(clientFullName) ||
                    transaction.getBeneficiaryName().equals(clientFullName)) &&
                    hasOpenComplianceIssue(transaction)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasOpenComplianceIssue(Transaction transaction) {
        List<Transaction.TransactionIssue> issues = transaction.getIssues();
        return issues != null && issues.stream().anyMatch(issue -> !issue.isIssueSolved());
    }

    /**
     * Returns all transactions indexed by beneficiary name.
     *
     * @return A map of transactions indexed by beneficiary name.
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        Map<String, Transaction> transactionsByBeneficiaryName = new HashMap<>();
        for (Transaction transaction : transactions) {
            transactionsByBeneficiaryName.put(transaction.getBeneficiaryName(), transaction);
        }
        return transactionsByBeneficiaryName;
    }

    /**
     * Returns the identifiers of all open compliance issues.
     *
     * @return A set of issue IDs.
     */
    public Set<Integer> getUnsolvedIssueIds() {
        Set<Integer> unsolvedIssueIds = new HashSet<>();
        for (Transaction transaction : transactions) {
            if (transaction.hasOpenComplianceIssue()) {
                List<Transaction.TransactionIssue> issues = transaction.getIssues();
                if (issues != null) {
                    unsolvedIssueIds.addAll(
                            issues.stream()
                                    .filter(issue -> !issue.isIssueSolved())
                                    .map(Transaction.TransactionIssue::getIssueId)
                                    .collect(Collectors.toList())
                    );
                }
            }
        }
        return unsolvedIssueIds;
    }

    /**
     * Returns a list of all solved issue messages.
     *
     * @return A list of issue messages.
     */
    public List<String> getAllSolvedIssueMessages() {
        List<String> solvedIssueMessages = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.hasOpenComplianceIssue()) {
                List<Transaction.TransactionIssue> issues = transaction.getIssues();
                if (issues != null) {
                    solvedIssueMessages.addAll(
                            issues.stream()
                                    .filter(Transaction.TransactionIssue::isIssueSolved)
                                    .map(Transaction.TransactionIssue::getIssueMessage)
                                    .collect(Collectors.toList())
                    );
                }
            }
        }
        return solvedIssueMessages;
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending.
     *
     * @return A list of the top 3 transactions.
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        List<Transaction> sortedTransactions = new ArrayList<>(transactions);
        sortedTransactions.sort(Comparator.comparingDouble(Transaction::getAmount).reversed());
        return sortedTransactions.stream().limit(3).collect(Collectors.toList());
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount.
     *
     * @return An optional containing the sender with the most total sent amount.
     */
    public Optional<String> getTopSender() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getSenderFullName, Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
}
