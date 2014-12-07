package se.tarnowski.xssworkbench.db;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {

    public static String JDBC_URL = "jdbc:hsqldb:mem:db";

    public DbHelper() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Couldn't load JDBC driver ", e);
        }
    }

    public void createTables() {
        Connection conn;
        Statement stmt;
        try {
            conn = DriverManager.getConnection(JDBC_URL, "SA", "");
            stmt = conn.createStatement();
            stmt.execute("CREATE TABLE transactions(customer_id BIGINT, description VARCHAR(255), amount BIGINT)");
            stmt.execute("INSERT INTO transactions (customer_id, description, amount) VALUES(1, 'Deposit', 1000)");
            stmt.execute("INSERT INTO transactions (customer_id, description, amount) VALUES(1, 'Withdrawal', 800)");
            String script = "<script>window.onload = function() {var link=document.getElementsByTagName(\"a\");link[0].href=\"https://hack.me\";}</script>";
            stmt.execute("INSERT INTO transactions (customer_id, description, amount) VALUES(2, 'Watch the logout button!" + script + "', 0)");
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        } catch (SQLException e) {
            throw new IllegalStateException("Couldn't create tables", e);
        }
    }
}
