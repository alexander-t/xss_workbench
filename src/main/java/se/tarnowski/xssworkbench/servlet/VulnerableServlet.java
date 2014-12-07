package se.tarnowski.xssworkbench.servlet;

import org.apache.commons.dbutils.DbUtils;
import se.tarnowski.xssworkbench.db.DbHelper;
import se.tarnowski.xssworkbench.service.TransactionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DISCLAIMER!
 * This code comes with security flaws and is meant for educational purposes only!
 * The author cannot be held responsible for any direct on indirect damage or any other consequences resulting
 * from running the code or parts of it.
 */
@WebServlet(name = "vulnerableServlet", urlPatterns = {"/vulnerable"})
public class VulnerableServlet extends HttpServlet {

    private Connection connection;
    private TransactionService transactionService;

    @Override
    public void init() throws ServletException {
        try {
            new DbHelper().createTables();
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:db", "SA", "");
            transactionService = new TransactionService(connection);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        DbUtils.closeQuietly(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vulnerable.jsp");

        createUncheckedLangAttribute(request);

        try {
            if (request.getParameter("cid") != null) {
                long customerId = Long.parseLong(request.getParameter("cid"));
                request.setAttribute("transactions", transactionService.getTransactions(customerId));
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            requestDispatcher = request.getRequestDispatcher("/error.jsp");
        }
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // Creates an unchecked parameter that will be sent to a hidden field to enable reflected XSS.
    private void createUncheckedLangAttribute(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        if (lang == null) {
            lang = "en";
        }
        request.setAttribute("lang", lang);
    }
}