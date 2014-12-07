<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--
DISCLAIMER!
This code comes with security flaws and is meant for educational purposes only!
The author cannot be held responsible for any direct on indirect damage or any other consequences resulting
from running the code or parts of it.
-->
<html>
<head>
    <title>XSS workbench - Vulnerable transaction listing page</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:set var="req" value="${pageContext.request}"/>
<div class="container">
    <div class="jumbotron">
        <h1>XSS Workbench</h1>

        <p>
            Call <code>${req.requestURL}?cid=1&lang=&lt;your malicious code here&gt;</code> to
            get the first customer's
            transactions and an opportunity to try reflected XSS.
        </p>

        <p>
            Call <code>${req.requestURL}?cid=2</code> to get the second customer's
            transactions and watch the reflected XSS work on the logout link.
        </p>
    </div>

    <div class="row">
        <div class="col-md-11"></div>
        <div class="col-md-1">
            <a class="btn btn-primary" href="http://www.sunet.se" role="button">Logout</a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Account details</h3>
                </div>
                <div class="panel-body">

                    <table class="table">
                        <thead>
                        <tr>
                            <th>Description</th>
                            <th>Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${transactions}" var="transaction">
                            <tr>
                                    <%-- This will escape HTML entities and make the page safe... --%>
                                    <%--<td><c:out value="${transaction.description}"/></td>--%>

                                    <%-- Whereas this will not! --%>
                                <td>${transaction.description}</td>
                                <td><c:out value="${transaction.amount}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-6"></div>
    </div>

    <form>
        <!-- This allows us to do reflected XSS :) -->
        <input type="hidden" name="lang" value="${lang}"/>
    </form>
</div>
</body>
</html>