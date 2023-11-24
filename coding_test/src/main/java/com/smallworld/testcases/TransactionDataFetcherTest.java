import com.smallworld.TransactionDataFetcher;
import com.smallworld.data.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TransactionDataFetcher} class.
 */
@ExtendWith(JUnitPlatform.class)
class TransactionDataFetcherTest {

    /**
     * Tests the {@link TransactionDataFetcher#getTotalTransactionAmount()} method.
     */
    @Test
    void testGetTotalTransactionAmount() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", false, 0, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        assertEquals(300.0, dataFetcher.getTotalTransactionAmount());
    }

    /**
     * Tests the {@link TransactionDataFetcher#getTotalTransactionAmountSentBy(String)} method.
     */
    @Test
    void testGetTotalTransactionAmountSentBy() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", false, 0, ""),
                new Transaction(50.0, "Sender1", "Beneficiary3", false, 0, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        assertEquals(150.0, dataFetcher.getTotalTransactionAmountSentBy("Sender1"));
    }

    /**
     * Tests the {@link TransactionDataFetcher#getMaxTransactionAmount()} method.
     */
    @Test
    void testGetMaxTransactionAmount() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", false, 0, ""),
                new Transaction(50.0, "Sender3", "Beneficiary3", false, 0, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        assertEquals(200.0, dataFetcher.getMaxTransactionAmount());
    }

    /**
     * Tests the {@link TransactionDataFetcher#countUniqueClients()} method.
     */
    @Test
    void testCountUniqueClients() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", false, 0, ""),
                new Transaction(50.0, "Sender1", "Beneficiary3", false, 0, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        assertEquals(3, dataFetcher.countUniqueClients());
    }

    /**
     * Tests the {@link TransactionDataFetcher#hasOpenComplianceIssues(String)} method.
     */
    @Test
    void testHasOpenComplianceIssues() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", true, 1, ""),
                new Transaction(50.0, "Sender3", "Beneficiary3", false, 0, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        assertTrue(dataFetcher.hasOpenComplianceIssues("Sender2"));
        assertFalse(dataFetcher.hasOpenComplianceIssues("Sender1"));
    }

    /**
     * Tests the {@link TransactionDataFetcher#getTransactionsByBeneficiaryName()} method.
     */
    @Test
    void testGetTransactionsByBeneficiaryName() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", false, 0, ""),
                new Transaction(50.0, "Sender3", "Beneficiary1", false, 0, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        assertEquals(2, dataFetcher.getTransactionsByBeneficiaryName().size());
    }

    /**
     * Tests the {@link TransactionDataFetcher#getUnsolvedIssueIds()} method.
     */
    @Test
    void testGetUnsolvedIssueIds() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", true, 1, ""),
                new Transaction(50.0, "Sender3", "Beneficiary3", true, 2, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
        assertEquals(2, unsolvedIssueIds.size());
        assertTrue(unsolvedIssueIds.contains(1));
        assertTrue(unsolvedIssueIds.contains(2));
    }

    /**
     * Tests the {@link TransactionDataFetcher#getAllSolvedIssueMessages()} method.
     */
    @Test
    void testGetAllSolvedIssueMessages() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", true, 1, "Issue 1"),
                new Transaction(50.0, "Sender3", "Beneficiary3", true, 2, "Issue 2")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
        assertEquals(2, solvedIssueMessages.size());
        assertTrue(solvedIssueMessages.contains("Issue 1"));
        assertTrue(solvedIssueMessages.contains("Issue 2"));
    }

    /**
     * Tests the {@link TransactionDataFetcher#getTop3TransactionsByAmount()} method.
     */
    @Test
    void testGetTop3TransactionsByAmount() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Sender1", "Beneficiary1", false, 0, ""),
                new Transaction(200.0, "Sender2", "Beneficiary2", false, 0, ""),
                new Transaction(50.0, "Sender3", "Beneficiary3", false, 0, "")
        );

        TransactionDataFetcher dataFetcher = new TransactionDataFetcher(transactions);
        List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();
        assertEquals(3, top3Transactions.size());
        assertEquals(200.0, top3Transactions.get(0).getAmount());
        assertEquals(100.0, top3Transactions.get(1).getAmount());
        assertEquals(50.0, top3Transactions.get(2).getAmount());
    }