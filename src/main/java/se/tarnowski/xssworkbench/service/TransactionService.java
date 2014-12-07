package se.tarnowski.xssworkbench.service;

import org.apache.commons.dbutils.DbUtils;
import se.tarnowski.xssworkbench.domain.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private Connection conn;

    public TransactionService(Connection conn) {
        this.conn = conn;
    }

    public List<Transaction> getTransactions(long customerId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT customer_id, description, amount FROM transactions WHERE customer_id=?");
            ps.setLong(1, customerId);
            rs = ps.executeQuery();

            List<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                transactions.add(new Transaction(rs.getLong("customer_id"),
                        rs.getString("description"), rs.getLong("amount")));
            }
            return transactions;
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(ps);
        }
    }
}
